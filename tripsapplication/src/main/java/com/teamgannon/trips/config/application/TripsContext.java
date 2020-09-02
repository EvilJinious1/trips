package com.teamgannon.trips.config.application;

import com.teamgannon.trips.config.application.model.AppViewPreferences;
import com.teamgannon.trips.search.SearchContext;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Data
//@Component
public class TripsContext {


    private AppViewPreferences appViewPreferences = new AppViewPreferences();

    private ApplicationPreferences appPreferences = new ApplicationPreferences();

    private SearchContext searchContext = new SearchContext();

    private DataSetContext dataSetContext = new DataSetContext();

}
