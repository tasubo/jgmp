package com.github.tasubo.jgmp;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MpAssert {
    public static HasParamMatcher hasParam(String v) {
        return new HasParamMatcher(v);
    }

    static class HasParamMatcher extends BaseMatcher<String> {

        private final String param;

        public HasParamMatcher(String value) {
            this.param = value;
        }

        @Override
        public boolean matches(Object item) {
            if (param.equals("v")) {
                return item.toString().indexOf("?v") > -1;
            }
            return item.toString().indexOf("&" + param) > -1;
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("present param: " + param);
        }

        public Matcher<? super String> withValue(String s) {
            return new HasParamValueMatcher(param, s);
        }

        public Matcher<? super String> withBareValue(String value) {
            return new HasParamValueMatcher(param, value, true);
        }
    }

    static class HasParamValueMatcher extends BaseMatcher<String> {

        private final String param;
        private final String value;
        private final boolean bare;

        public HasParamValueMatcher(String param, String value) {
            this.param = param;
            this.value = value;
            this.bare = false;
        }

        public HasParamValueMatcher(String param, String value, boolean bare) {
            this.param = param;
            this.value = value;
            this.bare = bare;
        }

        @Override
        public boolean matches(Object item) {

            String paramSearch = "&" + param;

            if (param.equals("v")) {
                paramSearch = "?v";
            }

            if (item.toString().indexOf(paramSearch) == -1) {
                return false;
            }


            String encoded = value;

            if (!bare) {
                encoded = URLParamEncoder.encode(value);
            }

            if (item.toString().indexOf(paramSearch + "=" + encoded) == -1) {
                return false;
            }


            return true;
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("Havent found param: " + param + ", with value: " + value);
        }
    }


    public static ParamsMatcherBuilder params(String params) {
        return new ParamsMatcherBuilder(params);
    }

    public static ParamMatcherBuilder param(String param) {
        return new ParamMatcherBuilder(param);
    }

    public static class ParamMatcherBuilder {

        private final String paramName;

        public ParamMatcherBuilder(String paramName) {
            this.paramName = paramName;
        }

        public Matcher<? super String> isLast() {
            return new BaseMatcher<String>() {
                @Override
                public boolean matches(Object item) {
                    String url = (String) item;
                    String str = "&" + paramName + "=";

                    if (url.indexOf(str) != url.lastIndexOf("&")) {
                        return false;
                    }

                    return true;
                }

                @Override
                public void describeTo(Description description) {
                    description.appendText(paramName + " is last");
                }
            };
        }

        public Matcher<? super String> isPresent() {
            return new BaseMatcher<String>() {
                @Override
                public boolean matches(Object item) {
                    String url = (String) item;

                    if (paramName.equals("v")) {
                        return url.contains("?v");
                    }
                    return item.toString().contains("&" + paramName);
                }

                @Override
                public void describeTo(Description description) {
                    description.appendText("present param: " + paramName);
                }
            };
        }

        public Matcher<? super String> appearsOnce() {
            return new BaseMatcher<String>() {
                @Override
                public boolean matches(Object item) {
                    String url = (String) item;
                    String paramString = "&" + paramName + "=";
                    if (!item.toString().contains(paramString)) {
                        return false;
                    }

                    int start = url.indexOf(paramString);

                    if (url.indexOf(paramString, start) > -1) {
                        return false;
                    }

                    return true;
                }

                @Override
                public void describeTo(Description description) {
                    description.appendText("appears once param: " + paramName);
                }
            };
        }
    }

    public static class ParamsMatcherBuilder {

        private final String paramName;

        public ParamsMatcherBuilder(String params) {
            this.paramName = params;
        }

        public Matcher<? super List<String>> allDiffer() {
            return new BaseMatcher<List<String>>() {
                @Override
                public boolean matches(Object item) {
                    List<String> urls = new ArrayList<String>((List<String>) item);
                    Set<String> values = new HashSet<String>();

                    for (String url : urls) {
                        String value = paramValue(paramName, url);
                        values.add(value);
                    }

                    return values.size() == urls.size();
                }

                @Override
                public void describeTo(Description description) {
                    description.appendText("All values are different");
                }
            };
        }

        private String paramValue(String paramName, String url) {
            int start = url.indexOf("&" + paramName) + 1 + paramName.length();
            int end = url.indexOf("&", start);

            if (end < 0) {
                end = url.length() - 1;
            }

            return url.substring(start, end);

        }
    }
}
