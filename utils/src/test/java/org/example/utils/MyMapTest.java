package org.example.utils;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@Tag("MyMapTests")
@DisplayName("MyMap Test Suite")
class MyMapTest {

    @Test
    void DefaultConstructorShouldCreateEmptyMap() {
        MyMap<String, Integer> m = new MyMap<>();
        assertTrue(m.isEmpty());
    }

    @org.junit.jupiter.api.Nested
    class ValidElementAddingTests {
        @Test
        @DisplayName("Put and size methods test")
        void MapShouldBeOfSizeFour() {
            MyMap<String, Integer> m = new MyMap<>();
            m.put("one", 1);
            m.put("two", 2);
            m.put("three", 3);
            m.put("four", 4);
            assertEquals(4, m.size());
        }

        @Test
        @DisplayName("Put, contains and containsValue methods test")
        void MapShouldContainSpecifiedKeysAndValues() {
            MyMap<String, Integer> m = new MyMap<>();
            m.put("one", 1);
            m.put("two", 2);
            m.put("three", 3);
            assertTrue(m.contains("one"));
            assertTrue(m.contains("two"));
            assertTrue(m.contains("three"));
            assert (m.containsValue(1));
            assertTrue(m.containsValue(2));
            assertTrue(m.containsValue(3));
        }

        @Test
        @DisplayName("Put method test")
        void PutShouldReplaceOldValueIfKeyAlreadyInMap() {
            MyMap<String, Integer> m = new MyMap<>();
            m.put("one", 1);
            m.put("two", 2);
            m.put("three", 3);
            assertEquals(3, m.get("three"));

            m.put("three", 6);
            assertEquals(6, m.get("three"));
        }

        @Test
        @DisplayName("Put, size, contains, containsValue methods test")
        void MapShouldNotAddNullElements() {
            MyMap<String, Integer> m = new MyMap<>();
            m.put("one", 1);
            m.put("two", 2);
            assertFalse(m.put("three", null));
            assertFalse(m.put(null, 4));
            assertFalse(m.put(null, null));
            assertEquals(2, m.size());
            assertFalse(m.contains(null));
            assertFalse(m.containsValue(null));
        }

        @Test
        @DisplayName("Put and get methods test")
        void GetMethodShouldReturnValidValueAfterPut() {
            MyMap<String, Integer> m = new MyMap<>();
            m.put("one", 1);
            m.put("two", 2);
            m.put("three", 3);
            m.put("four", 2);
            assertEquals(1, m.get("one"));
            assertEquals(2, m.get("two"));
            assertEquals(3, m.get("three"));
            assertEquals(2, m.get("four"));
        }
    }

    @org.junit.jupiter.api.Nested
    class RemoveMethodTests {
        @Test
        @DisplayName("Remove method test")
        void RemoveMethodShouldRemoveKeyAddedBefore() {
            MyMap<String, Integer> m = new MyMap<>();
            m.put("one", 1);
            m.put("two", 2);
            m.put("three", 3);
            assertEquals(3, m.size());

            m.remove("two");
            assertEquals(2, m.size());
            assertFalse(m.contains("two"));
            assertFalse(m.containsValue(2));
        }

        @Test
        @DisplayName("Remove method test")
        void RemoveShouldRemoveOnlyOneValueIfIsRepeated() {
            MyMap<String, Integer> m = new MyMap<>();
            m.put("one", 1);
            m.put("two", 2);
            m.put("three", 3);
            m.put("four", 2);
            assertEquals(4, m.size());

            m.remove("two");
            assertEquals(2, m.get("four"));
        }

        @Test
        @DisplayName("Remove method test")
        void RemoveShouldDoNothingIfKeyNotInMap() {
            MyMap<String, Integer> m = new MyMap<>();
            m.put("one", 1);
            m.put("two", 2);
            assertEquals(2, m.size());

            m.remove("three");
            assertEquals(2, m.size());

            m.remove(null);
            assertEquals(2, m.size());
        }
    }
}
