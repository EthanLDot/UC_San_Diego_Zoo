package com.example.zooseeker_team54;

import android.util.Pair;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class searchBarComponent {
    TextView textView;
    String query;

    public searchBarComponent(TextView textView) {
        this.textView = textView;
    }

    public void setQuery(String newQuery) {
        this.query = newQuery;
    }

    public String getQuery() {
        return this.query;
    }

    /**
     * Looks for a queried animal in the list of animals available at the zoo
     * @param exhibits Map of all exhibits in the zoo
     * @return sResults An List of strings representing the names of the animals that
     *                  contain the query string.
     */
    public List<Pair<String, String>> searchQuery(Map<String, ZooData.VertexInfo> exhibits) {
        List<Pair<String, String>> sResults = new ArrayList<>();
        if (query.length() == 0) {
            return sResults;
        }

        for(Map.Entry<String, ZooData.VertexInfo> entry :exhibits.entrySet()) {
            if (String.valueOf(entry.getValue().kind).toLowerCase().equals("exhibit")) {
                String entryVal = entry.getValue().name.toLowerCase();
                if (entryVal.contains(query) && !sResults.contains(entryVal)) {
                    sResults.add(new Pair<String, String>(entry.getValue().id, entry.getValue().name));
                }
            }
        }
        return sResults;
    }

}
