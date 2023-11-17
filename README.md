# Urban Community Charging Station Manager

## Overview

With the widespread adoption of electric vehicles, it's crucial to install accessible electric vehicle charging stations within urban communities. However, installing too many stations increases the overall installation and maintenance costs. 

This project focuses on developing software that determines which cities in an urban community should host a parking lot equipped with charging stations while meeting specific constraints:
- **Accessibility:** Each city must have its charging stations or be directly connected to a city that has them.
- **Economy:** The project's cost should be minimized, aiming to construct the fewest charging stations possible.

## Project Structure

### Packages

#### `backend`

Contains classes handling the backend logic of cities and urban communities.

- `City`: Represents a city with attributes and associated methods.
- `UrbanCommunity`: Manages urban communities, cities, routes, and verifies accessibility constraints.

#### `frontend`

Manages user interactions and menus for manipulating the urban community.

- `MenuControl`: Handles user interactions via menus for managing city connections, charging stations, and displaying options.
- `App`: Main class responsible for starting the user interface.

#### `exceptions`

Contains custom exceptions used within the project.

- `AccessibilityConstraintNotVerifiedException`: Thrown when the accessibility constraint is not met in the urban community.
- `ChargingPointNotFoundException`: Thrown when a charging point is not found for removal.
- `CityNotFoundException`: Thrown when a city is not found in the urban community.

## Usage

### Running the Application

To start the application, execute the `App` class's `main` method. This launches the user interface for managing the urban community charging stations.

### How to Use

1. Launch the application.
2. Load the urban community with cities and their names.
3. Use the menus to add routes between cities, manage charging stations, and view options.
4. The application verifies and displays cities with charging stations, ensuring accessibility constraints are met.
