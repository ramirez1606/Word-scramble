import javafx.application.Application; // Imports the Application class for JavaFX applications
import javafx.geometry.Insets; // Imports Insets for padding and margins
import javafx.geometry.Pos; // Imports Pos for alignment
import javafx.scene.Scene; // Imports Scene for the JavaFX scene
import javafx.scene.control.Button; // Imports Button control
import javafx.scene.control.ScrollPane; // Imports ScrollPane for scrollable content
import javafx.scene.control.TextArea; // Imports TextArea for multiline text input/display
import javafx.scene.image.Image; // Imports Image for displaying images
import javafx.scene.image.ImageView; // Imports ImageView for displaying images
import javafx.scene.layout.*; // Imports layout classes (BorderPane, HBox, VBox, GridPane)
import javafx.scene.text.Font; // Imports Font for text styling
import javafx.scene.text.FontWeight; // Imports FontWeight for bold text
import javafx.scene.text.Text; // Imports Text for displaying text
import javafx.stage.Stage; // Imports Stage for the application window
import java.util.HashMap; // Imports HashMap for storing key-value pairs
import java.util.Map; // Imports Map interface
import javafx.scene.control.TextField; //Imports TextField for single line text input

public class Region1Explorer extends Application { // Defines the main application class

    private String userRole; // Declares a private string variable to store the user's role

    public Region1Explorer(String userRole) { // Constructor to initialize the user role
        this.userRole = userRole; // Assigns the passed user role to the class variable
    }

    private String[][] provinces = { // Declares a 2D array to store province data
        {"Ilocos Norte", "Bangui Windmills", "d:/SAVED/Javaimages/Ilocosnorte/banguiwindmills_1x1.jpg",
         "Paoay Church", "d:/SAVED/Javaimages/Ilocosnorte/paoaychurch_1x1.JPG",
         "Kapurpurawan Rock Formation", "d:/SAVED/Javaimages/Ilocosnorte/kapurpurawanrockformation_1x1.jpg",
         "Sand Dunes Norte", "d:/SAVED/Javaimages/Ilocosnorte/sanddunesnorte_1x1.jpg",
         "Cape Bojeador Lighthouse", "d:/SAVED/Javaimages/Ilocosnorte/Cape Bojeador Lighthouse_1x1.jpg",
         "Patapat Viaduct", "d:/SAVED/Javaimages/Ilocosnorte/Patapat Viaduct_1x1.jpg",
         "Malacañang of the North", "d:/SAVED/Javaimages/Ilocosnorte/Malacañang of the North_1x1.jpg",
         "Pagudpud Beach", "d:/SAVED/Javaimages/Ilocosnorte/pagudpudbeach_1x1.png"},
    };

    private Map<String, String[]> landmarkImages = new HashMap<>(); // Declares a HashMap to store landmark images
    private GridPane gridPane; // Declares a GridPane to display landmarks

    private void populateLandmarkImages() { // Method to populate the landmark images map
        landmarkImages.put("Bangui Windmills", new String[]{ // Adds Bangui Windmills images to the map
            "d:/SAVED/Javaimages/Ilocosnorte/BanguiWindmills(1).jpg",
            "d:/SAVED/Javaimages/Ilocosnorte/BanguiWindmills.jpg",
            "d:/SAVED/Javaimages/Ilocosnorte/BanguiWindmills(2).jpg",
            "d:/SAVED/Javaimages/Ilocosnorte/BanguiWindmills(3).jpg",
            "d:/SAVED/Javaimages/Ilocosnorte/BanguiWindmills(4).jpg"
        });
    }

    private void searchLandmarks(String query) { // Method to search landmarks based on a query
        gridPane.getChildren().clear(); // Clears the GridPane

        int colIndex = 0; // Initializes column index
        int rowIndex = 0; // Initializes row index

        for (String[] province : provinces) { // Iterates through the provinces
            for (int i = 1; i < province.length; i += 2) { // Iterates through landmarks in each province
                String landmarkName = province[i]; // Gets the landmark name
                String imagePath = province[i + 1]; // Gets the image path

                if (landmarkName.toLowerCase().contains(query)) { // Checks if the landmark name contains the query
                    ImageView imageView = new ImageView(new Image("file:" + imagePath)); // Creates an ImageView
                    imageView.fitWidthProperty().bind(gridPane.widthProperty().divide(5)); // Sets image width
                    imageView.setPreserveRatio(true); // Preserves image aspect ratio

                    Button button = new Button(landmarkName); // Creates a button for the landmark
                    button.getStyleClass().add("landmark-button"); // Adds a style class to the button
                    button.setOnAction(e -> showLandmarkPage(landmarkName)); // Sets button action

                    VBox vbox = new VBox(10, imageView, button); // Creates a VBox to hold the image and button
                    vbox.setAlignment(Pos.CENTER); // Sets alignment
                    gridPane.add(vbox, colIndex, rowIndex); // Adds the VBox to the GridPane

                    colIndex++; // Increments column index
                    if (colIndex == 4) { // Checks if column index reaches 4
                        colIndex = 0; // Resets column index
                        rowIndex++; // Increments row index
                    }
                }
            }
        }
    }

    @Override
    public void start(Stage primaryStage) { // Start method for the JavaFX application
        populateLandmarkImages(); // Populates landmark images
        primaryStage.setFullScreen(false); // Sets full screen mode
        primaryStage.setMaximized(true); // Maximizes the window

        BorderPane root = new BorderPane(); // Creates a BorderPane as the root layout
        root.setStyle("-fx-background-color: #f4f4f4;"); // Sets background color

        HBox provinceButtons = new HBox(15); // Creates an HBox for province buttons
        VBox.setMargin(provinceButtons, new Insets(30)); // Sets margin
        provinceButtons.setAlignment(Pos.CENTER); // Sets alignment

        gridPane = new GridPane(); // Creates a GridPane
        gridPane.setAlignment(Pos.TOP_CENTER); // Sets alignment
        gridPane.setHgap(20); // Sets horizontal gap
        gridPane.setVgap(20); // Sets vertical gap

        TextField searchField = new TextField(); // Creates a TextField for search
        searchField.setPromptText("Search landmarks..."); // Sets prompt text
        searchField.setMaxWidth(300); // Sets max width

        Button searchButton = new Button("Search"); // Creates a search button
        searchButton.setOnAction(e -> { // Sets search button action
            String query = searchField.getText().trim().toLowerCase(); // Gets search query
            if (!query.isEmpty()) { // Checks if query is not empty
                searchLandmarks(query); // Searches landmarks

                if (selectedProvinceButton != null) { // Resets selected province button style
                    selectedProvinceButton.getStyleClass().remove("province-button-selected");
                    selectedProvinceButton = null;
                }
            }
        });

        Button clearButton = new Button("Clear"); // Creates clear button
        clearButton.setOnAction(e -> { // Sets clear button action
            searchField.clear(); // Clears search field
            gridPane.getChildren().clear(); // Clears grid pane

            if (selectedProvinceButton != null) { // Resets selected province button style
                selectedProvinceButton.getStyleClass().remove("province-button-selected");
                selectedProvinceButton = null;
            }
        });

        HBox topBar = new HBox(30); // Creates HBox for search and province buttons
        topBar.setAlignment(Pos.CENTER); // Sets alignment
        topBar.setPadding(new Insets(20)); // Sets padding

        HBox searchBox = new HBox(10, searchField, searchButton, clearButton); // Creates HBox for search elements
        searchBox.setAlignment(Pos.CENTER_LEFT); // Sets alignment

        topBar.getChildren().addAll(searchBox, provinceButtons); // Adds search elements and province buttons to top bar

        VBox topContainer = new VBox(topBar); // Creates VBox for top bar
        topContainer.setAlignment(Pos.CENTER); // Sets alignment

        for (String[] province : provinces) { // Iterates through provinces
            final String[] selectedProvinceData = province; // Stores province data
            Button provinceButton = new Button(province[0]); // Creates a province button
            provinceButton.getStyleClass().add("province-button"); // Adds style class

            provinceButton.setOnAction(e -> { // Sets button action
                displayProvince(selectedProvinceData); // Displays province landmarks

                if (selectedProvinceButton != null) { // Resets previously selected button style
                    selectedProvinceButton.getStyleClass().remove("province-button-selected");
                }

                provinceButton.getStyleClass().add("province-button-selected"); // Highlights selected button
                selectedProvinceButton = provinceButton; // Stores the selected button
            });

            provinceButtons.getChildren().add(provinceButton); // Adds button to province buttons HBox
        }

        root.setTop(topContainer); // Sets top container in BorderPane
        ScrollPane scrollPane = new ScrollPane(gridPane); // Creates a ScrollPane for the gridPane
        scrollPane.setFitToWidth(true); // Sets to fit width
        scrollPane.setFitToHeight(true); // Sets to fit height
        scrollPane.setPadding(new Insets(20)); // Sets Padding
        scrollPane.setStyle("-fx-background-color:transparent;"); // Sets transparent background

        root.setCenter(scrollPane); // Sets the scroll pane in the center of the BorderPane
        scrollPane.setFitToHeight(false); // Disable fit to height.

        Button logoutButton = new Button("Logout"); // Creates a logout button
        logoutButton.setOnAction(e -> { // Sets logout button action
            primaryStage.close(); // Closes the main window
            Stage loginStage = new Stage(); // Creates a new stage for login
            new LoginSystem().start(loginStage); // Opens the login window
        });

        HBox logoutContainer = new HBox(logoutButton); // Creates HBox for logout button
        logoutContainer.setAlignment(Pos.TOP_RIGHT); // Sets alignment
        logoutContainer.setPadding(new Insets(10)); // Sets padding

        root.setBottom(logoutContainer); // Sets logout container in the bottom of the BorderPane

        Scene scene = new Scene(root, 950, 650); // Creates a Scene
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm()); // Adds stylesheet

        primaryStage.setTitle("Region 1 Explorer"); // Sets the window title
        primaryStage.setScene(scene); // Sets the scene
        primaryStage.show(); // Shows the window
    }

    private Button selectedProvinceButton = null; // Stores the selected province button

    private void showLandmarkPage(String landmarkName) { // Method to show landmark details page
        Stage landmarkStage = new Stage(); // Creates a new stage
        landmarkStage.setTitle(landmarkName); // Sets the title
        landmarkStage.setFullScreen(false); // Sets full screen mode
        landmarkStage.setMaximized(true); // Maximizes the stage

        VBox layout = new VBox(15); // Creates a VBox layout
        layout.setAlignment(Pos.CENTER); // Sets alignment
        layout.setPadding(new Insets(20)); // Sets padding

        Text title = new Text(landmarkName); // Creates a title text
        title.setFont(Font.font("Arial", FontWeight.BOLD, 32)); // Sets font

        ImageView imageView = new ImageView(); // Creates an ImageView
        imageView.setFitWidth(600); // Sets width
        imageView.setPreserveRatio(true); // Preserves ratio

        String[] images = landmarkImages.get(landmarkName); // Gets images for the landmark
        String details = LandmarkDAO.getLandmarkDetails(landmarkName); // Gets details from DB
        final int[] currentIndex = {0}; // Current index for images

        if (images != null && images.length > 0) { // Checks if images exist
            imageView.setImage(new Image("file:" + images[currentIndex[0]])); // Sets initial image
        }

        Button prevButton = new Button("<"); // Creates previous button
        Button nextButton = new Button(">"); // Creates next button

        prevButton.setOnAction(e -> { // Sets previous button action
            currentIndex[0] = (currentIndex[0] - 1 + images.length) % images.length; // Calculates previous index
            imageView.setImage(new Image("file:" + images[currentIndex[0]])); // Sets previous image
        });

        nextButton.setOnAction(e -> { // Sets next button action
            currentIndex[0] = (currentIndex[0] + 1) % images.length; // Calculates next index
            imageView.setImage(new Image("file:" + images[currentIndex[0]])); // Sets next image
        });

        HBox navButtons = new HBox(10, prevButton, nextButton); // Creates HBox for navigation buttons
        navButtons.setAlignment(Pos.CENTER); // Sets alignment

        TextArea detailsArea = new TextArea(details); // Creates a TextArea for details
        detailsArea.setWrapText(true); // Sets wrap text
        detailsArea.setPrefHeight(400); // Sets preferred height
        detailsArea.setStyle("-fx-font-size: 18px;"); // Sets font size

        if (!userRole.equals("Admin")) { // Checks if user is not admin
            detailsArea.setEditable(false); // Disables editing for non-admins
        }

        ScrollPane scrollPane = new ScrollPane(detailsArea); // Creates a scroll pane for the details
        scrollPane.setFitToWidth(true); // Sets fit to width
        scrollPane.setFitToHeight(true); // Sets fit to height

        Button backButton = new Button("Back"); // Creates back button
        backButton.setOnAction(e -> landmarkStage.close()); // Closes the landmark stage

        Button saveButton = new Button("Save Changes"); // Creates save button
        saveButton.setVisible(userRole.equals("Admin")); // Shows only for admin
        saveButton.setOnAction(e -> { // Sets save button action
            String newDetails = detailsArea.getText(); // Gets new details
            LandmarkDAO.saveLandmark(landmarkName, newDetails); // Saves landmark details
        });

        HBox bottomPanel = new HBox(10, backButton, saveButton); // Creates HBox for bottom buttons
        bottomPanel.setAlignment(Pos.CENTER); // Sets alignment

        layout.getChildren().addAll(title, imageView, navButtons, scrollPane, bottomPanel); // Adds elements to layout

        Scene scene = new Scene(layout, 800, 600); // Creates a new scene
        landmarkStage.setScene(scene); // Sets the scene
        landmarkStage.show(); // Shows the landmark stage
    }

    private void displayProvince(String[] province) { // Method to display landmarks for a province
        gridPane.getChildren().clear(); // Clears the grid pane

        ColumnConstraints column = new ColumnConstraints(); // Creates column constrains
        column.setPercentWidth(25); // Sets column width to 25%
        gridPane.getColumnConstraints().clear(); // Clears any existing column constraints
        for (int i = 0; i < 4; i++) { // Adds 4 column constraints
            gridPane.getColumnConstraints().add(column);
        }

        int colIndex = 0; // Initializes column index
        int rowIndex = 0; // Initializes row index

        for (int i = 1; i < province.length; i += 2) { // Iterates through landmarks
            String landmarkName = province[i]; // Gets landmark name
            String imagePath = province[i + 1]; // Gets image path

            ImageView imageView = new ImageView(new Image("file:" + imagePath)); // Creates ImageView
            imageView.fitWidthProperty().bind(gridPane.widthProperty().divide(5)); // Sets image width
            imageView.setPreserveRatio(true); // Preserves ratio
            imageView.setStyle("-fx-border-radius: 10px;"); // Sets border radius

            Button button = new Button(landmarkName); // Creates landmark button
            button.getStyleClass().add("landmark-button"); // Adds style class
            button.setOnAction(e -> showLandmarkPage(landmarkName)); // Sets button action

            VBox vbox = new VBox(10, imageView, button); // Creates VBox
            vbox.setAlignment(Pos.CENTER); // Sets alignment
            gridPane.add(vbox, colIndex, rowIndex); // Adds VBox to GridPane

            colIndex++; // Increments column index
            if (colIndex == 4) { // Checks if column index reaches 4
                colIndex = 0; // Resets column index
                rowIndex++; // Increments row index
            }
        }
    }

    public static void main(String[] args) { // Main method
        DatabaseManager.createTable(); // Creates database table
        LoginSystem.main(args); // Launches the login system
    }
}