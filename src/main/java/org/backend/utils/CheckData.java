package org.backend.utils;

import java.nio.file.Path;

public class CheckData implements Check {
    @Override
    public boolean checkDataEmployees(String[] data) {
        return false;
    }

    @Override
    public boolean checkSourcePathData(Path path) {
        return false;
    }

    @Override
    public boolean checkDataPosts(String[] data) {
        return false;
    }
}
