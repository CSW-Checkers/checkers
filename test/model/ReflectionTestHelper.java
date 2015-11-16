package model;

import java.lang.reflect.Field;

public class ReflectionTestHelper {
    public static void changePrivateFieldOnObject(Object object, String fieldName,
            Object newValue) {
        try {
            final Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(object, newValue);
        } catch (final NoSuchFieldException e) {
            e.printStackTrace();
        } catch (final SecurityException e) {
            e.printStackTrace();
        } catch (final IllegalArgumentException e) {
            e.printStackTrace();
        } catch (final IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    public static Object getPrivateFieldValue(Object object, String fieldName) {
        try {
            final Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(object);
        } catch (final NoSuchFieldException e) {
            e.printStackTrace();
        } catch (final SecurityException e) {
            e.printStackTrace();
        } catch (final IllegalArgumentException e) {
            e.printStackTrace();
        } catch (final IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
