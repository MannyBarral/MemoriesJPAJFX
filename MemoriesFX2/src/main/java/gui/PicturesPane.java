package gui;

import domain.Picture;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import persist.PictureDM;

/**
 * This layout class contains the instances of PictureViews shown as a list of
 * pages. The number of PictureView per page is fixed and set in the contructor.
 *
 * @author tl
 */
public class PicturesPane extends VBox {

    private List<domain.Picture> pictures;
    private RootPane root;
    private Pagination pagination;
    private int nbImagesPerPage;
    private List<PictureView> pictureViews = new ArrayList<>();
    private List<PictureView> selectedPictureViews = new ArrayList<>();


    public PicturesPane(RootPane root, Collection<domain.Picture> images,
                        int nbImagesPerPage) {
        super();
        this.root = root;
        this.nbImagesPerPage = nbImagesPerPage;
        setPictures(images);
    }

    /**
     * This method is used to set or substitute the set of pictures visible in
     * the PicturesPane.
     *
     * @param searchResult
     */
    public void setPictures(Collection<Picture> searchResult) {
        // If search result is not empty
        if (searchResult != null && searchResult.size() > 0) {
            // TODO: un-select images in search result
            this.pictures = new ArrayList<>(searchResult);
        }
        pagination = new Pagination(nbPages());
        pagination.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer pageIndex) {
                return createPage(pageIndex);
            }
        });
        this.getChildren().add(new Label("PicturesPane"));
        this.getChildren().clear();
        this.getChildren().add(pagination);
        VBox.setVgrow(pagination, Priority.ALWAYS);
    }

    /**
     * This method is used in the pagination process.
     *
     * @param pageIndex
     * @return
     */
    private VBox createPage(int pageIndex) {
        VBox box = new VBox();
        if (pageIndex * this.nbImagesPerPage > pictures.size()) {
            pageIndex = 0;
        }
        int page = pageIndex * this.nbImagesPerPage;
        List<domain.Picture> pageItems
                = pictures.subList(page,
                                   (page + this.nbImagesPerPage < pictures.
                                           size()) ? page + this.nbImagesPerPage : pictures.
                                                   size());

        for (domain.Picture m : pageItems) {
            PictureView mv = new PictureView(root, m);
            box.getChildren().add(mv);
            //add mv to the list:
            this.pictureViews.add(mv);
            
        }
        box.setSpacing(5);
        return box;
    }

    private int nbPages() {
        return (int) Math.ceil(1.0 * pictures.size() / this.nbImagesPerPage);
    }

    public void addTagToSelectedPictures(TagView tagView) {
        // for all selected images, add the corresponding tag.
    	for (PictureView pv : this.pictureViews) {
    		
    		if(pv.isSelected()) {
    			pv.addTag(tagView);
    			System.out.println("Tag Added to Picture: " + pv.getPicture().getFilePath());
    		}
    	}
    	
    	
    }

    public void removeTagFromPictures(TagView tv) {
        // for all selected pictures remove the corresponding tag
    	for (PictureView pv : this.pictureViews) {
    		if(pv.isSelected()) {
    			pv.removeTag(tv);
    		}
    	}
    }
    
    public List<PictureView> getPictureViews() {
        return pictureViews;
    }
    
    public List<PictureView> getSelectedPictureViews() {
        return selectedPictureViews;
    }
    

}

