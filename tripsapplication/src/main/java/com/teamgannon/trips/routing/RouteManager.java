package com.teamgannon.trips.routing;

import com.teamgannon.trips.graphics.entities.RouteDescriptor;
import com.teamgannon.trips.graphics.entities.*;
import com.teamgannon.trips.jpa.model.DataSetDescriptor;
import com.teamgannon.trips.listener.RouteUpdaterListener;
import javafx.geometry.Point3D;
import javafx.scene.Node;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class RouteManager {

    private DataSetDescriptor dataSetDescriptor;
    private final RouteUpdaterListener routeUpdaterListener;
    private Map<UUID, Node> starLookup;

    private final double lineWidth = 0.5;

    /**
     * this is the descriptor of the current route
     */
    private RouteDescriptor currentRoute;
    /**
     * the graphic portion of the current route
     */
    private Xform currentRouteDisplay = new Xform();
    /**
     * whether there is a route being traced, true is yes
     */
    private boolean routingActive = false;

    /**
     * the total set of all routes
     */
    private final Xform routesGroup = new Xform();

    ///////////////////////

    /**
     * the constructor
     *
     * @param routeUpdaterListener the route update listener
     */
    public RouteManager(RouteUpdaterListener routeUpdaterListener, Map<UUID, Node> starLookup) {
        this.routeUpdaterListener = routeUpdaterListener;
        this.starLookup = starLookup;

        currentRouteDisplay = new Xform();
        currentRouteDisplay.setWhatAmI("Current Route");

        // define the
        routesGroup.setWhatAmI("Star Routes");
    }

    /////////////// general

    /**
     * set the data descriptor descriptor context
     *
     * @param dataSetDescriptor the new current context
     */
    public void setDatasetContext(DataSetDescriptor dataSetDescriptor) {
        this.dataSetDescriptor = dataSetDescriptor;
    }

    /**
     * clear the routes
     */
    public void clearRoutes() {
        // clear the routes
        routesGroup.getChildren().clear();
    }

    /**
     * get the total routes being displayed
     *
     * @return the entire routes set
     */
    public Node getRoutesGroup() {
        return this.routesGroup;
    }

    /**
     * toggle the routes
     *
     * @param routesOn the status of the routes
     */
    public void toggleRoutes(boolean routesOn) {
        routesGroup.setVisible(routesOn);
    }

    ///////////// routing functions

    public void startRoute(RouteDescriptor routeDescriptor, Map<String, String> properties) {
        if (routingActive) {
            resetRoute();
        }
        routingActive = true;
        currentRoute = routeDescriptor;
        log.info("Start charting the route:" + routeDescriptor);
        double x = Double.parseDouble(properties.get("x"));
        double y = Double.parseDouble(properties.get("y"));
        double z = Double.parseDouble(properties.get("z"));
        UUID id = UUID.fromString(properties.get("recordId"));
        if (currentRoute != null) {
            Point3D toPoint3D = new Point3D(x, y, z);
            currentRoute.getLineSegments().add(toPoint3D);
            currentRoute.getRouteList().add(id);
            routesGroup.getChildren().add(currentRouteDisplay);
            routesGroup.setVisible(true);
        }
    }


    public void continueRoute(Map<String, String> properties) {
        if (routingActive) {
            createRouteSegment(properties);
            log.info("Next Routing step:{}", currentRoute);
        }
    }


    public void finishRoute(Map<String, String> properties) {
        createRouteSegment(properties);
        routingActive = false;
        makeRoutePermanent(currentRoute);
        routeUpdaterListener.newRoute(dataSetDescriptor, currentRoute);
    }

    public void makeRoutePermanent(com.teamgannon.trips.graphics.entities.RouteDescriptor currentRoute) {
        // remove our hand drawn route
        routesGroup.getChildren().remove(currentRouteDisplay);

        // create a new one based on descriptor
        Xform displayRoute = createDisplayRoute(currentRoute);

        // add this created one to the routes group
        routesGroup.getChildren().add(displayRoute);
    }


    public Xform createDisplayRoute(RouteDescriptor currentRoute) {
        Xform route = new Xform();
        route.setWhatAmI(currentRoute.getName());
        Point3D previousPoint = new Point3D(0, 0, 0);
        boolean firstPoint = true;
        for (Point3D point3D : currentRoute.getLineSegments()) {
            if (firstPoint) {
                firstPoint = false;
            } else {
                Node lineSegment = CustomObjectFactory.createLineSegment(previousPoint, point3D, lineWidth, currentRoute.getColor());
                route.getChildren().add(lineSegment);
            }
            previousPoint = point3D;
        }
        return route;
    }

    public void createRouteSegment(Map<String, String> properties) {
        double x = Double.parseDouble(properties.get("x"));
        double y = Double.parseDouble(properties.get("y"));
        double z = Double.parseDouble(properties.get("z"));
        UUID id = UUID.fromString(properties.get("recordId"));

        if (currentRoute != null) {
            int size = currentRoute.getLineSegments().size();
            Point3D fromPoint = currentRoute.getLineSegments().get(size - 1);
            Point3D toPoint3D = new Point3D(x, y, z);
            Node lineSegment = CustomObjectFactory.createLineSegment(
                    fromPoint, toPoint3D, 0.5, currentRoute.getColor()
            );
            currentRouteDisplay.getChildren().add(lineSegment);
            currentRoute.getLineSegments().add(toPoint3D);
            currentRoute.getRouteList().add(id);
            currentRouteDisplay.setVisible(true);
        }
    }

    /**
     * reset the route and remove the parts that were partially drawn
     */
    public void resetRoute() {
        if (currentRoute != null) {
            currentRoute.clear();
        }
        routesGroup.getChildren().remove(currentRouteDisplay);
        routingActive = false;
        createCurrentRouteDisplay();
        resetCurrentRoute();
        log.info("Resetting the route");
    }


    public void createCurrentRouteDisplay() {
        currentRouteDisplay = new Xform();
        currentRouteDisplay.setWhatAmI("Current Route");
    }

    /**
     * reset the current route
     */
    public void resetCurrentRoute() {
        currentRoute.clear();
        currentRouteDisplay = new Xform();
        currentRouteDisplay.setWhatAmI("Current Route");
    }

    ////////////  redraw the routes

    /**
     * plot the routes
     *
     * @param routeList the list of routes
     */
    public void plotRoutes(List<Route> routeList) {
        // clear existing routes
        routesGroup.getChildren().clear();
        routeList.forEach(this::plotRoute);
    }

    ////////

    /**
     * plot a single route
     *
     * @param route the route to plot
     */
    public void plotRoute(Route route) {
        if (checkIfRouteCanBePlotted(route)) {
            // do actual plot
            RouteDescriptor routeDescriptor = toRouteDescriptor(route);
            Xform routeGraphic = StellarEntityFactory.createRoute(routeDescriptor);
            routesGroup.getChildren().add(routeGraphic);
            routesGroup.setVisible(true);
        }
        log.info("Plot done");
    }

    /**
     * convert a database description of a route to a graphical one.
     * check that all the stars in the original route are present because we can't display the route
     *
     * @param route the db description of a route
     * @return the graphical descriptor
     */
    private RouteDescriptor toRouteDescriptor(Route route) {
        RouteDescriptor routeDescriptor = RouteDescriptor.toRouteDescriptor(route);
        for (UUID id : route.getRouteStars()) {
            StarDisplayRecord starDisplayRecord = getStar(id);
            if (starDisplayRecord != null) {
                routeDescriptor.getRouteList().add(id);
                routeDescriptor.getLineSegments().add(starDisplayRecord.getCoordinates());
            }
        }

        return routeDescriptor;
    }

    public boolean checkIfRouteCanBePlotted(Route route) {
        return route.getRouteStars().stream().allMatch(id -> starLookup.containsKey(id));
    }

    /**
     * get the embedded object associated with the star in the lookup
     *
     * @param starId the id
     * @return the embedded object
     */
    public StarDisplayRecord getStar(UUID starId) {
        Node star = starLookup.get(starId);
        if (star != null) {
            return (StarDisplayRecord) star.getUserData();
        } else {
            return null;
        }
    }

}