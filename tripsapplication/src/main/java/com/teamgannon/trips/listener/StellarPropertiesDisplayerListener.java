package com.teamgannon.trips.listener;

import com.teamgannon.trips.graphics.entities.StarDisplayRecord;
import com.teamgannon.trips.jpa.model.AstrographicObject;

import java.util.Map;

/**
 * Used to display a property set for a stellar object
 * <p>
 * Created by larrymitchell on 2017-02-02.
 */
public interface StellarPropertiesDisplayerListener {

    void displayStellarProperties(AstrographicObject astrographicObject);

}