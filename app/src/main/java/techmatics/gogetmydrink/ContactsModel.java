package techmatics.gogetmydrink;

/**
 * Created by admin on 13/09/18.
 */

public class ContactsModel {
    String name;
    String imageUrl;
    String contactNumber;
    int rating;
    public ContactsModel(String name,String image,String contactNumber,int rating){
        this.name=name;
        this.imageUrl=image;
        this.contactNumber=contactNumber;
        this.rating=rating;
    }

    public String getName() {
        return name;
    }

    public int getRating() {
        return rating;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
