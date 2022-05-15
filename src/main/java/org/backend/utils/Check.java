package org.backend.utils;

import java.nio.file.Path;

public interface Check {
    boolean checkDataEmployees(String[] data);

    boolean checkSourcePathData(Path path);

    boolean checkDataPosts(String[] data);
}
