package org.vaadin.johannes;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * The main view contains a button and a click listener.
 */
@Route("")
@PWA(name = "Project Base for Vaadin Flow", shortName = "Project Base")
public class MainView extends VerticalLayout {

    private final Grid<Song> grid;
    private final List<Song> songs;

    public MainView() {
        grid = new Grid<>();
        grid.setWidth("500px");
        grid.setHeight("300px");
        Grid.Column<Song> nameColumn = grid.addColumn(Song::getName).setHeader("Name").setSortable(true);
        Grid.Column<Song> artistColumn = grid.addColumn(Song::getArtist).setHeader("Artist").setSortable(true);
        Grid.Column<Song> popColumn = grid.addColumn(Song::getPop).setHeader("Pop");

        songs = createListOfSongs();
        grid.setDataProvider(
                DataProvider.fromCallbacks(query -> {
                    query.getLimit();
                    query.getOffset();
                    return songs.stream();
                }, query -> {
                    query.getOffset();
                    query.getLimit();
                    return songs.size();
                }));

        TextField firstName = new TextField("name");
        TextField lastName = new TextField("artist");
        ComboBox<Boolean> isPopCombobox = new ComboBox<>();
        isPopCombobox.setItems(Boolean.TRUE, Boolean.FALSE);
        isPopCombobox.setItemLabelGenerator(String::valueOf);

        nameColumn.setEditorComponent(firstName);
        artistColumn.setEditorComponent(lastName);
        popColumn.setEditorComponent(isPopCombobox);

        Binder<Song> editBinder = new Binder<>(Song.class);
        editBinder.bind(firstName, Song::getName, Song::setName);
        editBinder.bind(lastName, Song::getArtist, Song::setArtist);
        editBinder.bind(isPopCombobox, Song::isPop, Song::setPop);

        Editor<Song> editor = grid.getEditor();
        editor.setBuffered(true);
        editor.setBinder(editBinder);

        Button addItem = new Button("Add item", e -> {
            Song newSong;
            songs.add(newSong = new Song("", ""));
            grid.getDataProvider().refreshAll();
//            Collection<Song> items = ((ListDataProvider<Song>) grid.getDataProvider()).getItems();
//            items.add(newSong = new Song(".", ".", false));
            grid.getDataCommunicator().refresh(newSong);
            grid.getEditor().editItem(newSong);
            isPopCombobox.focus();
        });

        grid.addComponentColumn(item -> new Checkbox("Test")).setHeader("Actions");
        add(grid, addItem);

    }

    private List<Song> createListOfSongs() {
        List<Song> songs = new ArrayList<>(Arrays.asList(new Song("sasdsad1", "aasdsad1"), new Song("asadsad", "werwrew"), new Song("sdfsdfsd", "xcvxcvxc")));
        for (int i = 0; i < 0; i++) {
            songs.add(new Song("Song" + i, "Artist" + i, i % 3 == 0));
        }
        return songs;
    }
}
