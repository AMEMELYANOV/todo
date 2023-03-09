package ru.job4j.todo.util;

import ru.job4j.todo.model.User;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Утилитный класс для работы со временем и временными зонами
 * @author Alexander Emelyanov
 * @version 1.0
 */
public final class DateTmeUtil {

    /**
     * Приватный конструктор класса для исключения наследования.
     *
     *@exception AssertionError выбрасывается при попытке вызвать конструктор
     */
    private DateTmeUtil() {
        throw new AssertionError();
    }

    /**
     * Выполняет составление списка временных зон в Java.
     *
     * @return список временных зон
     */
    public static List<String> getTimeZoneIds() {
        Set<String> zoneIds = ZoneId.getAvailableZoneIds();
        List<String> zones = new ArrayList<>(zoneIds);
        Collections.sort(zones);
        return zones;
    }

    /**
     * Возвращает временную зону пользователя, если временная зона
     * отсутствует, то вернется временная зона 'Europe/London'
     *
     * @param user пользователь
     * @return временная зона пользователя
     */
    public static String getUserTimeZone(User user) {
        return user.getTimezone() != null ? user.getTimezone() : "Europe/London";
    }
}
