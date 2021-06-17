package pers.cocoadel.java.learning.local;

import javax.swing.*;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class EnumCombo<T> extends JComboBox<String> {

    private final Map<String, T> table = new HashMap<>();

    public EnumCombo(Class<?> clazz, String... labels) {
        for (String label : labels) {
            String name = label.toUpperCase().replace(" ", "_");
            try {
                Field field = clazz.getField(name);
                @SuppressWarnings("unchecked")
                T value = (T) field.get(clazz);
                table.put(label, value);
            } catch (Exception e) {
                label = "(" + label + ")";
                table.put(label, null);
            }
            addItem(label);
        }
        setSelectedItem(labels[0]);
    }

    public T getValue() {
        return table.get(getSelectedItem());
    }
}
