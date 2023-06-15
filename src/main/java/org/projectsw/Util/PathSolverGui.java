package org.projectsw.Util;

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

    public static String boxLogo() {
        return standardInitialPath+"LogoAndIcons/Box280x280"+formatPng;
    }

    public static String icon() {
        return standardInitialPath+"LogoAndIcons/Icon"+formatPng;
    }

    public static String publisher() {
        return standardInitialPath+"LogoAndIcons/Publisher"+formatPng;
    }

    public static String title618() {
        return standardInitialPath+"LogoAndIcons/Title2000x618"+formatPng;
    }

    public static String title2000() {
        return standardInitialPath+"LogoAndIcons/Title2000x2000"+formatPng;
    }

    public static String endGameToken() {
        return standardInitialPath+"ScoringTokens/EndGame"+formatJpg;
    }

    public static String ScoringToken2() {
        return standardInitialPath+"ScoringTokens/Scoring2"+formatJpg;
    }

    public static String ScoringToken4() {
        return standardInitialPath+"ScoringTokens/Scoring4"+formatJpg;
    }

    public static String ScoringToken6() {
        return standardInitialPath+"ScoringTokens/Scoring6"+formatJpg;
    }

    public static String ScoringToken8() {
        return standardInitialPath+"ScoringTokens/Scoring8"+formatJpg;
    }

    public static String ScoringTokenEmpty1() {
        return standardInitialPath+"ScoringTokens/ScoringEmpty1"+formatJpg;
    }

    public static String ScoringTokenEmpty2() {
        return standardInitialPath+"ScoringTokens/ScoringEmpty2"+formatJpg;
    }

    public static String ScoringTokenBack() {
        return standardInitialPath+"ScoringTokens/ScoringBack"+formatJpg;
    }

    public static String FirstPlayerToken() {
        return standardInitialPath+"Various/FirstPlayerToken"+formatPng;
    }

    public static String Books(int code){
        return standardInitialPath+"Tiles/Books"+code+formatPng;
    }

    public static String Cats(int code){
        return standardInitialPath+"Tiles/Cats"+code+formatPng;
    }

    public static String Frames(int code){
        return standardInitialPath+"Tiles/Frames"+code+formatPng;
    }

    public static String Games(int code){
        return standardInitialPath+"Tiles/Games"+code+formatPng;
    }

    public static String Plants(int code){
        return standardInitialPath+"Tiles/Plants"+code+formatPng;
    }

    public static String Trophies(int code){
        return standardInitialPath+"Tiles/Trophies"+code+formatPng;
    }
}
