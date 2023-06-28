package org.projectsw.Util;

/**
 * Pathsolver class
 */
public class PathSolverGui {

    private static final String standardInitialPath = "src/main/resources/ImagesGui/";
    private static final String formatJpg = ".jpg";
    private static final String formatPng = ".png";

    public static String bannerPath(){
        return standardInitialPath+"/Backgrounds/Banner"+formatPng;
    }

    public static String basePagePath(){
        return standardInitialPath+"/Backgrounds/BasePage"+formatJpg;
    }

    public static String squareParquetPath(){
        return standardInitialPath+"/Backgrounds/SquareParquet"+formatJpg;
    }

    public static String closedBagPath(){
        return standardInitialPath+"/Bags/ClosedBag"+formatPng;
    }

    public static String openBagPath(){
        return standardInitialPath+"/Bags/OpenBag"+formatPng;
    }

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

    public static String personalGoalBack(){
        return standardInitialPath+"/PersonalGoals/back"+formatJpg;
    }

    public static String personalGoalEmpty(){
        return standardInitialPath+"/PersonalGoals/empty"+formatJpg;
    }

}
