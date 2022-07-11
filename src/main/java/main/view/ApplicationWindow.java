/**
 *   Copyright (C) 2022  Kasper Engelen
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package main.view;

import main.model.index.DocumentIndex;
import main.model.index.DocumentIndexReader;
import main.model.index.DocumentIndexWriter;
import main.model.validation.IndexValidationException;
import org.oxbow.swingbits.dialog.task.TaskDialogs;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class that represents the main window of the application.
 */
public class ApplicationWindow
{
    private final JFrame window;
    private final JTable table;

    /**
     * Constructor.
     */
    public ApplicationWindow() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        // @see https://docs.oracle.com/javase/tutorial/uiswing/lookandfeel/nimbus.html
        boolean look_and_feel_found = false;
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    look_and_feel_found = true;
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            look_and_feel_found = true;
        }

        if(!look_and_feel_found) {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }

        // set window properties
        window = new JFrame("Document Manager");
        window.setLocation(250,250);
        window.setSize(1000, 750);
        window.setLayout(new BorderLayout());
        //window.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // initialise the buttons
        initButtons();

        // set table properties
        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        window.getContentPane().add(BorderLayout.CENTER, scrollPane);
        table.setFillsViewportHeight(true);

        // add the URI renderer
        URIRenderer renderer = new URIRenderer();
        table.setDefaultRenderer(URI.class, renderer);
        table.addMouseListener(renderer);
        table.addMouseMotionListener(renderer);

        window.setVisible(true);
    }

    /**
     * Initialise the buttons at the bottom of the window.
     */
    private void initButtons() {
        JPanel bottom_panel = new JPanel(new BorderLayout());
        window.getContentPane().add(BorderLayout.SOUTH, bottom_panel);

        JPanel button_panel = new JPanel();
        bottom_panel.add(BorderLayout.NORTH, button_panel);

        JButton new_index_button = new JButton("New Index");
        button_panel.add(new_index_button);

        new_index_button.addActionListener(e -> this.showCreateIndexDialog());

        JButton open_index_button = new JButton("Open Existing Index");
        button_panel.add(open_index_button);

        open_index_button.addActionListener(e -> this.showOpenIndexDialog());

        JButton add_entry = new JButton("Add Entry");
        button_panel.add(add_entry);

        add_entry.addActionListener(e -> JOptionPane.showMessageDialog(window, "Future functionality."));

        JButton import_entries = new JButton("Import Entries");
        button_panel.add(import_entries);

        import_entries.addActionListener(e -> JOptionPane.showMessageDialog(window, "Future functionality."));

        JButton export_entries = new JButton("Export Entries");
        button_panel.add(export_entries);

        export_entries.addActionListener(e -> JOptionPane.showMessageDialog(window, "Future functionality."));
    }

    /**
     * Dialog to create a new index.
     */
    private void showCreateIndexDialog()
    {
        JFileChooser fileChooser = new JFileChooser();

        // initial directory
        fileChooser.setCurrentDirectory(new File("."));

        // make it so that only JSON file are shown
        fileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                if(f.isDirectory()) {
                    return true;
                } else {
                    return f.getName().toLowerCase().endsWith(".json");
                }
            }

            @Override
            public String getDescription() {
                return "JSON Files (*.json)";
            }
        });
        int result = fileChooser.showSaveDialog(window);

        if(result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            // check if the file exists
            if(selectedFile.exists()) {
                boolean doOverwrite = TaskDialogs.isConfirmed(this.window, "Overwrite file?", "File already exists, are you sure you want to overwrite file?");

                if(!doOverwrite) {
                    return;
                }
            }

            // create the file
            try {
                DocumentIndexWriter writer = new DocumentIndexWriter(new DocumentIndex());
                writer.write(selectedFile.toPath());
            } catch (IOException e) {
                TaskDialogs.showException(e);
                return;
            }

            // load the file we just created
            this.loadIndexFile(selectedFile.toPath());
        }
    }

    /**
     * Dialog to open existing index.
     */
    private void showOpenIndexDialog()
    {
        // @see https://stackoverflow.com/questions/19302029/filter-file-types-with-jfilechooser
        // @see https://mail.codejava.net/java-se/swing/show-simple-open-file-dialog-using-jfilechooser

        JFileChooser fileChooser = new JFileChooser();

        // initial directory
        fileChooser.setCurrentDirectory(new File("."));

        // make it so that only JSON file are shown
        fileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                if(f.isDirectory()) {
                    return true;
                } else {
                    return f.getName().toLowerCase().endsWith(".json");
                }
            }

            @Override
            public String getDescription() {
                return "JSON Files (*.json)";
            }
        });
        int result = fileChooser.showOpenDialog(window);

        if(result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            if(!selectedFile.exists()) {
                TaskDialogs.error(window, "Error while loading file!", "Cannot find file \"%s\"".formatted(selectedFile.toString()));
                return;
            }

            if(!selectedFile.canRead()) {
                TaskDialogs.error(window, "Error while loading file!", "Cannot access file \"%s\"".formatted(selectedFile.toString()));
                return;
            }

            this.loadIndexFile(selectedFile.toPath());
        }
    }

    /**
     * Load the specified index file and use it to populate the table.
     *
     * @param filename The path to the index file.
     */
    private void loadIndexFile(Path filename) {
        try {
            DocumentIndexReader reader = new DocumentIndexReader(filename);
            DocumentIndex docIndex = reader.read();

            this.window.setTitle("Document Manager -- " + filename.toString());
            this.initialiseTable(docIndex.getDocumentList().stream().map(DocumentView::new).collect(Collectors.toList()));


        } catch (IndexValidationException e) {
            this.initialiseTable(List.of());
            TaskDialogs.error(window, "Error while loading file!", String.join("\n", e.getErrorMessages()));

        } catch (IOException e) {
            this.initialiseTable(List.of());
            TaskDialogs.showException(e);
        }
    }

    /**
     * Populate the table with the specified document views.
     */
    private void initialiseTable(List<DocumentView> views) {
        DocumentTableModel docTableModel = new DocumentTableModel(views);
        table.setModel(docTableModel);

        // set the width of reach column
        for (int colNr = 0; colNr < docTableModel.getColumnCount(); colNr++) {
            int width = docTableModel.getColumnWidth(colNr);

            if (width > 0) {
                TableColumn column = table.getColumnModel().getColumn(colNr);
                column.setMinWidth(width);
                column.setMaxWidth(width);
            }
        }

        // set row height for better readability
        table.setRowHeight(25);
    }
}




