package org.projectsw.Util;

/**
 * Pathsolver class
 */
public class PathSolverGui {

    private static final String standardInitialPath = "src/main/resources/ImagesGui/";
    private static final String formatJpg = ".jpg";
    private static final String formatPng = ".png";

    public static String boardPath(){
        return standardInitialPath+"/Boards/Board"+formatPng;
    }

    public static String shelfPath(){
        return standardInitialPath+"/Boards/Shelf"+formatPng;
    }

    public static String commonGoalPath(int code){
        return standardInitialPath+"/CommonGoals/"+code+formatJpg;
    }

    public static String commonGoalBack(){
        return standardInitialPath+"/CommonGoals/back"+formatJpg;
    }

    public static String personalGoalPath(int code){
        return standardInitialPath+"/PersonalGoals/"+code+formatPng;
    }

    public static String icon() {
        return standardInitialPath+"LogoAndIcons/Icon"+formatPng;
    }

    public static String books(int code){
        return standardInitialPath+"Tiles/Books"+code+formatPng;
    }

    public static String cats(int code){
        return standardInitialPath+"Tiles/Cats"+code+formatPng;
    }

    public static String frames(int code){
        return standardInitialPath+"Tiles/Frames"+code+formatPng;
    }

    public static String games(int code){
        return standardInitialPath+"Tiles/Games"+code+formatPng;
    }

    public static String plants(int code){
        return standardInitialPath+"Tiles/Plants"+code+formatPng;
    }

    public static String trophies(int code){
        return standardInitialPath+"Tiles/Trophies"+code+formatPng;
    }
}
