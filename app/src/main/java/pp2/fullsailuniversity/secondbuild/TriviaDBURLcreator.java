package pp2.fullsailuniversity.secondbuild;

public class TriviaDBURLcreator {

    public static String createURL(String category, int amount, String difficulty) throws java.io.IOException
    {

        int categoryAsInt;
        StringBuilder newURL = new StringBuilder();
        newURL.append("https://opentdb.com/api.php");
        if (amount >= 9)
        {
            newURL.append("?amount=" + amount);
        }
        else //by default gets 10 questions otherwise
            newURL.append("?amount=10");

        if (category != null)
        {
            switch (category)
            {
                case "General Knowledge":
                    categoryAsInt = 9;
                break;
                case "History":
                    categoryAsInt = 23;
                break;
                case "Politics":
                    categoryAsInt = 24;
                break;
                case "Mythology":
                    categoryAsInt = 20;
                break;
                case "Geography":
                    categoryAsInt = 22;
                break;
                default:
                    categoryAsInt = 0;
                break;
            }
                //if not valid category string passed in, get any random category by default
             if (categoryAsInt != 0) newURL.append("&category=" +categoryAsInt);
        }


        if (difficulty != null)
        {
            switch (difficulty)
            {
                case "Easy": newURL.append("&difficulty=easy");
                    break;
                case "Medium": newURL.append("&difficulty=medium");
                    break;
                case "Hard": newURL.append("&difficulty=hard");
                    break;
                default:
                    break;
                    // if no specific difficulty selected, allow all kinds to be used
            }
        }
        //currently only support multi-choice
        //TODO add code to support true/false as well
        newURL.append("&type=multiple");

        return newURL.toString();

    }
//end of class
//
}
