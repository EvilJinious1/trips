package com.teamgannon.trips.listener;

import com.teamgannon.trips.jpa.model.AstrographicObject;
import com.teamgannon.trips.jpa.model.DataSetDescriptor;
import com.teamgannon.trips.search.AstroSearchQuery;

import java.util.List;

public interface StellarDataUpdaterListener {

    /**
     * do a plot update based on new search query
     *
     * @param searchQuery the search query
     * @param showPlot    show the grphical plot
     * @param showTable   show the table
     */
    void showNewStellarData(AstroSearchQuery searchQuery, boolean showPlot, boolean showTable);

    /**
     * do a plot update based on default query
     *
     * @param showPlot  show the grphical plot
     * @param showTable show the table
     */
    void showNewStellarData(boolean showPlot, boolean showTable);

    /**
     * add the data set descriptor
     *
     * @param dataSetDescriptor the dataset descriptor
     */
    void addDataSet(DataSetDescriptor dataSetDescriptor);

    /**
     * remove the data set descriptor
     *
     * @param dataSetDescriptor the dataset descriptor
     */
    void removeDataSet(DataSetDescriptor dataSetDescriptor);

    /**
     * set the contextual dataset
     *
     * @param descriptor the dataset descript that is in context
     */
    void setContextDataSet(DataSetDescriptor descriptor);

    /**
     * get the astro object from the db on new search query
     *
     * @return the list of objects
     */
    List<AstrographicObject> getAstrographicObjectsOnQuery();

    /**
     * add a star to the db
     *
     * @param astrographicObject the star
     */
    void addStar(AstrographicObject astrographicObject);

    /**
     * update the star
     *
     * @param astrographicObject the star to update
     */
    void updateStar(AstrographicObject astrographicObject);

    /**
     * remove the specified star
     *
     * @param astrographicObject the star to remove
     */
    void removeStar(AstrographicObject astrographicObject);


}
