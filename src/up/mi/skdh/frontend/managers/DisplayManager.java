package up.mi.skdh.frontend.managers;

import java.awt.Dimension;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import up.mi.skdh.backend.City;
import up.mi.skdh.backend.UrbanCommunity;

public class DisplayManager {
	private static void renderGraph(GraphicsContext gc, CircleLayout<City, String> layout) {
		for (City city : layout.getGraph().getVertices()) {
            double x = layout.getX(city);
            double y = layout.getY(city);
            
            if (city.hasChargingPoint()) {
                gc.setFill(Color.RED);
            } else {
                gc.setFill(Color.rgb(44, 117, 241));
            }

            gc.fillOval(x, y, 20, 20);
            gc.setFill(Color.BLACK);
            Font originalFont = gc.getFont();
            Font boldFont = Font.font(originalFont.getFamily(), FontWeight.BOLD, originalFont.getSize());
            gc.setFont(boldFont);
            gc.fillText(city.getName(), x - 5, y - 5);
            

            for (String edge : layout.getGraph().getOutEdges(city)) {
                City neighbor = layout.getGraph().getOpposite(city, edge);
                double nx = layout.getX(neighbor);
                double ny = layout.getY(neighbor);

                gc.strokeLine(x + 10, y + 10, nx + 10, ny + 10);
            }
        }
		gc.setFill(Color.RED);
	    gc.fillOval(400, 10, 20, 20); // Position for red color representation
	    gc.setFill(Color.BLACK);
	    gc.fillText("Avec", 430, 25); // Label for red color representation

	    gc.setFill(Color.rgb(44, 117, 241));
	    gc.fillOval(400, 40, 20, 20); // Position for blue color representation
	    gc.setFill(Color.BLACK);
	    gc.fillText("Sans", 430, 55); // Label for blue color representation
    }
	
	public static Canvas createGraphVisualization(UrbanCommunity community) {
		 UndirectedSparseGraph<City, String> graph = createGraph(community);

        CircleLayout<City, String> layout = new CircleLayout<>(graph);
        layout.setSize(new Dimension(500, 500));
        layout.initialize();

        Canvas canvas = new Canvas(500, 500);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        renderGraph(gc, layout);

        return canvas;
    }

	private static UndirectedSparseGraph<City, String> createGraph(UrbanCommunity community) {
		UndirectedSparseGraph<City, String> graph = new UndirectedSparseGraph<>();

        for (City city : community.getCities()) {
            graph.addVertex(city);
            for (City neighbor : city.getNeighbors()) {
                String edgeId = city.getName() + "-" + neighbor.getName();
                if (!graph.containsEdge(edgeId)) {
                    graph.addEdge(edgeId, city, neighbor);
                }
            }
        }

        return graph;
    }
	
	public static Label displayUrbanCommunity(UrbanCommunity community) {
        StringBuilder labelContent = new StringBuilder("Villes de la communauté : ");
        for (City city : community.getCities()) {
            labelContent.append(city.getName()).append("  ");
        }
        return new Label(labelContent.toString());
    }

    public static Label displayCitiesWithChargingPoint(UrbanCommunity community) {
        StringBuilder labelContent = new StringBuilder("Villes avec une zone de recharge : ");
        for (City city : community.getCities()) {
            if (city.hasChargingPoint()) {
                labelContent.append(city.getName()).append("  ");
            }
        }
        return new Label(labelContent.toString());
    }

    public static Label displayCitiesWithNoChargingPoint(UrbanCommunity community) {
    	boolean foundCityWithoutChargingPoint = false;
        StringBuilder labelContent = new StringBuilder("Villes sans une zone de recharge : ");
        for (City city : community.getCities()) {
            if (!city.hasChargingPoint()) {
                labelContent.append(city.getName()).append("  ");
                foundCityWithoutChargingPoint = true;
            }
        }
        if (!foundCityWithoutChargingPoint) {
            return new Label("Villes sans une zone de recharge : Aucune");
        } else {
            return new Label(labelContent.toString());
        }
    }
}
