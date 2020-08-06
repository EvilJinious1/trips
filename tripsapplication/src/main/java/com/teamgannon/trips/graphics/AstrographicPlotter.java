package com.teamgannon.trips.graphics;

import com.teamgannon.trips.algorithms.Universe;
import com.teamgannon.trips.config.application.TripsContext;
import com.teamgannon.trips.graphics.entities.StarDisplayRecord;
import com.teamgannon.trips.graphics.panes.InterstellarSpacePane;
import com.teamgannon.trips.jpa.model.AstrographicObject;
import com.teamgannon.trips.search.SearchContext;
import com.teamgannon.trips.starmodel.StarBase;
import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * This plots a astrographic list of object to the panes
 * <p>
 * Created by larrymitchell on 2017-02-13.
 */
@Slf4j
@Component
public class AstrographicPlotter {

    /**
     * we do this to make the star size a constant size bigger x3
     */
    private final static int GRAPHICS_FUDGE_FACTOR = 3;

    private final StarBase starBase;
    private final SearchContext searchContext;

    private final AstrographicTransformer astrographicTransformer;

    /**
     * the drawing surface for the astrographic plotter
     */
    private InterstellarSpacePane interstellarSpacePane;

    /**
     * for dependency injection
     *
     * @param starBase the in memory starbase
     */
    public AstrographicPlotter(
            StarBase starBase,
            TripsContext tripsContext) {
        this.starBase = starBase;
        this.searchContext = tripsContext.getSearchContext();
        this.astrographicTransformer = new AstrographicTransformer(tripsContext.getAppPreferences().getGridsize());
    }

    public static Color getColor(double[] colors) {
        return Color.color(colors[0], colors[1], colors[2]);
    }

    /**
     * set the pane for plotting stars
     *
     * @param interstellarSpacePane the drawing pane
     */
    public void setInterstellarPane(InterstellarSpacePane interstellarSpacePane) {
        this.interstellarSpacePane = interstellarSpacePane;
    }

    /**
     * draw the ch view file
     *
     * @param astrographicObjects the list of objects to draw
     * @param centerCoordinates   the center of the plot
     */
    public void drawAstrographicData(List<AstrographicObject> astrographicObjects, double[] centerCoordinates) {

        // clear old drawing
        interstellarSpacePane.clearStars();

        // set the records to the in memory cache for quick access
        starBase.setRecords(astrographicObjects);

        // find the min/max values to plot
        astrographicTransformer.findMinMaxValues(astrographicObjects, centerCoordinates);
        ScalingParameters scalingParameters = astrographicTransformer.getScalingParameters();
        interstellarSpacePane.rebuildGrid(scalingParameters.getScaleIncrement(), scalingParameters.getGridScale());

        // plot all stars
        for (AstrographicObject astrographicObject : astrographicObjects) {
            try {
                // create a star record object
                double[] ords = astrographicObject.getCoordinates();
                double[] correctedOrds = astrographicTransformer.transformOrds(ords);
                String starName = astrographicObject.getDisplayName();
                Color starColor = astrographicObject.getStarColor();

                // we create a 3x radius based on stars much smaller than Sol
                double radius = astrographicObject.getRadius() * GRAPHICS_FUDGE_FACTOR;

                // draw the star
                if (drawable(astrographicObject)) {
                    StarDisplayRecord record
                            = StarDisplayRecord.builder()
                            .starName(starName)
                            .recordId(astrographicObject.getId())
                            .starColor(starColor)
                            .radius(radius)
                            .actualCoordinates(ords)
                            .coordinates(new Point3D(correctedOrds[0], correctedOrds[1], correctedOrds[2]))
                            .build();
                    interstellarSpacePane.drawStar(record, searchContext.getAstroSearchQuery().getCenterStar());
                } else {
                    log.warn("star record is not drawable:{}", astrographicObject);
                }
            } catch (IllegalArgumentException iae) {
                log.error("Star color is invalid:{}", astrographicObject);
            }
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * check if the point is in the drawable area
     *
     * @param starRecord the list of ordinates
     * @return true if in the area and false otherwise
     */
    private boolean drawable(AstrographicObject starRecord) {
        double[] ordinates = starRecord.getCoordinates();
        if (ordinates[0] > Universe.boxWidth) {
            return false;
        }
        if (ordinates[1] > Universe.boxHeight) {
            return false;
        }
        return !(ordinates[2] > Universe.boxDepth);
    }

}
