# iLink : Urban Community Charging Station Manager

## Overview

The growing prevalence of electric vehicles necessitates accessible charging stations within urban communities. However, deploying numerous stations escalates installation and maintenance costs.

This project focuses on software development that identifies optimal cities in an urban community to host charging stations, considering specific constraints:
- **Accessibility:** Each city should have its charging stations or direct access to a city with them.
- **Economy:** Minimize costs by constructing the fewest possible charging stations.

## Project Structure

### Packages

#### `backend`

Contains classes handling the backend logic of cities and urban communities.

- `City`: Represents a city with associated attributes and methods.
- `UrbanCommunity`: Manages urban communities, cities, routes, and verifies accessibility constraints.

#### `exceptions`

Custom exceptions used within the project.

- `AccessibilityConstraintNotVerifiedException`: Indicates an unmet accessibility constraint.
- `ChargingPointNotFoundException`: Thrown when a charging point isn't found for removal.
- `CityNotFoundException`: Signifies a city's absence in the urban community.
- `ChargingPointFoundException`: Indicates the discovery of a charging point during addition.

#### `frontend`

Manages user interactions and interfaces for manipulating the urban community. Organized into various packages.

- `App`: Main class initializing the application.
- `MainApp`: Core class for the graphical user interface.
- `MenuControl`: Handles command-line user interactions for managing city connections, charging stations, and displaying options.

##### `managers`

Manages different aspects of the application.

- `CommunityManager`: Oversees the urban community.
- `FileManager`: Deals with file operations.
- `DisplayManager`: Manages information display.

##### `layouts`

Defines graphical interface page layouts.

## Usage

### Running the Application

Starting the application:
- If providing a file path, use the command-line version.
- Without a file path, launch the graphical user interface.

Steps:
1. Launch the application.
2. Load the urban community with cities and their names.
3. Use menus or the graphical interface to add city routes, manage charging stations, and explore options.
4. Verify and display cities with charging stations, ensuring accessibility constraints are met.

### Prerequisites

For the graphical user interface functionality, ensure you have the following frameworks installed:
- [JavaFX](https://openjfx.io/) for GUI functionality.
- [JUNG](http://jung.sourceforge.net/) for graph-based modeling and analysis.

These frameworks are required for the graphical interface to function correctly.
