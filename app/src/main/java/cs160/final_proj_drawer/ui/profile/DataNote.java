package cs160.final_proj_drawer.ui.profile;

//todo please let me know what this is in the group chat
public class DataNote
{
    String text;
    String comment;
    String date;

    public DataNote(String text, String comment, String date)
    {
        this.text = text;
        this.comment = comment;
        this.date = date;
    }

    public String getText()
    {
        return text;
    }

    public String getComment()
    {
        return comment;
    }

    public String getDate()
    {
        return date;
    }
}