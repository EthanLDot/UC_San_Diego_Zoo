package com.example.zooseeker_team54;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import android.widget.TextView;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

@RunWith(AndroidJUnit4.class)
public class planRouteTest {
//    @Test
//    public void sampleTest() {
//        try (ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class)){
//            scenario.moveToState(Lifecycle.State.CREATED);
//            scenario.onActivity(activity -> {
//                assertEquals(4, 2 + 2);
//                TextView planSize = activity.findViewById(R.id.planSize);
//                assertEquals("8", planSize.getText().toString());
//            });
//        }
//    }

    @Test
    public void failingTest() {
        assertEquals("There is no way this test shoudl pass", "Yep, there's no way");
    }

           /*System.out.printf("The shortest path from '%s' to '%s' is:\n", start, goal);

        int i = 1;
        for (IdentifiedWeightedEdge e : path.getEdgeList()) {
            System.out.printf("  %d. Walk %.0f meters along %s from '%s' to '%s'.\n",
                    i,
                    g.getEdgeWeight(e),
                    eInfo.get(e.getId()).street,
                    vInfo.get(g.getEdgeSource(e).toString()).name,
                    vInfo.get(g.getEdgeTarget(e).toString()).name);
            i++;
        }*/
}