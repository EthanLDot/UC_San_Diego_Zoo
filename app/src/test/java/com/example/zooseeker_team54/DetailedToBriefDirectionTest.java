package com.example.zooseeker_team54;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;


public class DetailedToBriefDirectionTest {
    List<LocEdge> directions = new ArrayList<>();
    List<LocEdge> correct = new ArrayList<>();
    List<LocEdge> solutions = new ArrayList<>();

    @Test
    public void FourAllIntersectionTest() {
        directions.add(new LocEdge("edge0", 1, "Sesame_Street",
                "Exhibit_A","Exhibit_A_ID", "Exhibit_B", "Exhibit_B_ID" ));
        directions.add(new LocEdge("edge1", 3, "Seventh_Street",
                "Exhibit_B","Exhibit_B_ID", "Exhibit_C", "Exhibit_C_ID" ));
        directions.add(new LocEdge("edge2", 5, "Revelle_Street",
                "Exhibit_C","Exhibit_C_ID", "Exhibit_D", "Exhibit_D_ID" ));
        directions.add(new LocEdge("edge3", 8, "Sixth_Street",
                "Exhibit_D","Exhibit_D_ID", "Exhibit_E", "Exhibit_E_ID" ));

        List<LocEdge> briefRoute = Utilities.getBriefDirections(directions);
        solutions.add(new LocEdge("edge0", 1, "Sesame_Street",
                "Exhibit_A","Exhibit_A_ID", "Exhibit_B", "Exhibit_B_ID" ));
        solutions.add(new LocEdge("edge1", 3, "Seventh_Street",
                "Exhibit_B","Exhibit_B_ID", "Exhibit_C", "Exhibit_C_ID" ));
        solutions.add(new LocEdge("edge2", 5, "Revelle_Street",
                "Exhibit_C","Exhibit_C_ID", "Exhibit_D", "Exhibit_D_ID" ));
        solutions.add(new LocEdge("edge3", 8, "Sixth_Street",
                "Exhibit_D","Exhibit_D_ID", "Exhibit_E", "Exhibit_E_ID" ));

        assertEquals(briefRoute.toString(), solutions.toString());

    }

    @Test
    public void twoExhibitOneStreetTest() {
        directions.add(new LocEdge("edge0", 1, "mestreet", "source",
                "sourceID", "mid", "midID"));
        directions.add(new LocEdge("edge1", 1, "mestreet", "mid",
                "midID", "sink", "sinkID"));

        List<LocEdge> briefRoute = Utilities.getBriefDirections(directions);
        correct.add(new LocEdge("edge0", 2, "mestreet", "source",
                "sourceID", "sink", "sinkID"));

        assertEquals(correct.toString(), briefRoute.toString());
    }

    @Test
    public void intersectTo2StraightTest() {
        directions.add(new LocEdge("edge0", 1, "youStreet", "source",
                "sourceID", "mid1", "mid1ID"));
        directions.add(new LocEdge("edge1", 1, "mestreet", "mid1",
                "mid1ID", "mid2", "mid2ID"));
        directions.add(new LocEdge("edge2", 1, "mestreet", "mid2",
                "mid2ID", "sink", "sinkID"));
        List<LocEdge> briefRoute = Utilities.getBriefDirections(directions);

        correct.add(new LocEdge("edge0", 1, "youStreet", "source",
                "sourceID", "mid1", "mid1ID"));
        correct.add(new LocEdge("edge1", 2, "mestreet", "mid1",
                "mid1ID", "sink", "sinkID"));

        assertEquals(correct.toString(), briefRoute.toString());
    }

    @Test
    public void intersectTo2StraightToIntersectTest() {
        directions.add(new LocEdge("edge0", 1, "youStreet", "source",
                "sourceID", "mid1", "mid1ID"));
        directions.add(new LocEdge("edge1", 1, "mestreet", "mid1",
                "mid1ID", "mid2", "mid2ID"));
        directions.add(new LocEdge("edge2", 1, "mestreet", "mid2",
                "mid2ID", "mid3", "mid3ID"));
        directions.add(new LocEdge("edge3", 1, "theyStreet", "mid3",
                "mid3ID", "sink", "sinkID"));
        List<LocEdge> briefRoute = Utilities.getBriefDirections(directions);

        correct.add(new LocEdge("edge0", 1, "youStreet", "source",
                "sourceID", "mid1", "mid1ID"));
        correct.add(new LocEdge("edge1", 2, "mestreet", "mid1",
                "mid1ID", "mid3", "mid3ID"));
        correct.add(new LocEdge("edge3", 1, "theyStreet", "mid3",
                "mid3ID", "sink", "sinkID"));

        assertEquals(correct.toString(), briefRoute.toString());
    }

    @Test
    public void emptyTest() {
        assertEquals(correct.toString(), solutions.toString());
    }

    @Test
    public void StraightIntersectionStraightTest() {
        directions.add(new LocEdge("edge0", 1, "Sesame_Street",
                "Exhibit_A","Exhibit_A_ID", "Exhibit_B", "Exhibit_B_ID" ));
        directions.add(new LocEdge("edge1", 3, "Sesame_Street",
                "Exhibit_B","Exhibit_B_ID", "Exhibit_C", "Exhibit_C_ID" ));
        directions.add(new LocEdge("edge2", 5, "Revelle_Street",
                "Exhibit_C","Exhibit_C_ID", "Exhibit_D", "Exhibit_D_ID" ));
        directions.add(new LocEdge("edge3", 8, "Revelle_Street",
                "Exhibit_D","Exhibit_D_ID", "Exhibit_E", "Exhibit_E_ID" ));

        List<LocEdge> briefRoute = Utilities.getBriefDirections(directions);
        solutions.add(new LocEdge("edge0", 4, "Sesame_Street",
                "Exhibit_A","Exhibit_A_ID", "Exhibit_C", "Exhibit_C_ID" ));
        solutions.add(new LocEdge("edge1", 13, "Revelle_Street",
                "Exhibit_C","Exhibit_C_ID", "Exhibit_E", "Exhibit_E_ID" ));

        assertEquals(briefRoute.toString(), solutions.toString());
    }
}
