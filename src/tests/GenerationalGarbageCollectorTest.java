package tests;

import memory.GenerationalGarbageCollector;
import org.junit.Test;
import static org.junit.Assert.*;

public class GenerationalGarbageCollectorTest {

    @Test
    public void testComprehensiveGC() {
        GenerationalGarbageCollector gc = new GenerationalGarbageCollector(3, 3);

        // Add objects to the young generation
        gc.addHeapObject("object1");
        gc.addHeapObject("object2");
        gc.addHeapObject("object3");

        // Add references
        gc.addReference("object1", "object2");
        gc.addReference("object2", "object3");

        // Add more objects to trigger automatic GC in young generation
        gc.addHeapObject("object4");
        gc.addHeapObject("object5");

        // Assert the size of young generation after GC
        assertEquals(2, gc.memUse());

        // Promote objects to old generation
        gc.addHeapObject("object6");
        gc.addHeapObject("object7");
        gc.addHeapObject("object8");

        // Add references
        gc.addReference("object4", "object5");
        gc.addReference("object5", "object6");

        // Trigger automatic GC in old generation
        gc.addHeapObject("object9");

        // Assert the size of old generation after GC
        assertEquals(3, gc.memUse());

        // Remove references and trigger GC
        gc.removeReference("object4", "object5");
        gc.removeReference("object5", "object6");
        gc.collectGarbage(gc.getGeneration("object4"));

        // Assert the size of memory after removing references and GC
        assertEquals(3, gc.memUse());
    }






    @Test
    public void testAddHeapObject() {
        GenerationalGarbageCollector gc = new GenerationalGarbageCollector(2, 2);
        gc.addHeapObject("object1");
        gc.addHeapObject("object2");
        assertEquals(2, gc.memUse());
    }

    @Test
    public void testAddReference() {
        GenerationalGarbageCollector gc = new GenerationalGarbageCollector(2, 2);
        gc.addHeapObject("object1");
        gc.addHeapObject("object2");
        gc.addReference("object1", "object2");
        assertTrue(gc.isInMemory("object1"));
        assertTrue(gc.isInMemory("object2"));
    }

    @Test
    public void testAutomaticGarbageCollectionYoung() {
        GenerationalGarbageCollector gc = new GenerationalGarbageCollector(2, 2);
        gc.addHeapObject("object1");
        gc.addHeapObject("object2");
        gc.addHeapObject("object3"); // This should trigger GC for young generation
        assertEquals(2, gc.memUse());
    }

    @Test
    public void testAutomaticGarbageCollectionOld() {
        GenerationalGarbageCollector gc = new GenerationalGarbageCollector(2, 2);
        gc.addHeapObject("object1");
        gc.addHeapObject("object2");
        gc.addHeapObject("object3");
        gc.addHeapObject("object4");
        gc.addHeapObject("object5"); // This should trigger GC for old generation
        assertEquals(2, gc.memUse());
    }

    @Test
    public void testPromotionAndRemoveReference() {
        GenerationalGarbageCollector gc = new GenerationalGarbageCollector(2, 2);
        gc.addHeapObject("object1");
        gc.addHeapObject("object2");
        gc.addHeapObject("object3");
        gc.addHeapObject("object4");
        gc.addHeapObject("object5");
        gc.addHeapObject("object6"); // This should trigger promotion
        gc.addReference("object1", "object2");
        gc.removeReference("object1", "object2");
        assertFalse(gc.isInMemory("object2"));
    }
}