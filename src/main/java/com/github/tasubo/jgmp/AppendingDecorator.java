package com.github.tasubo.jgmp;

final class AppendingDecorator implements Decorating {
    private final String decoratorsList;

    private final String appending;

    AppendingDecorator() {
        decoratorsList = "";
        appending = "";
    }

    AppendingDecorator(AppendingDecorator appendingDecorator, Decorating decorating) {
        String classNameEntry = decorating.getClass().getCanonicalName() + ";";
        if (!appendingDecorator.decoratorsList.contains(classNameEntry)) {
            String with = decorating.getPart();
            appending = appendingDecorator.appending + with;
            decoratorsList = appendingDecorator.decoratorsList + classNameEntry;
        } else {
            decoratorsList = appendingDecorator.decoratorsList;
            appending = appendingDecorator.appending;
        }
    }

    @Override
    public String getPart() {
        return appending;
    }
}
