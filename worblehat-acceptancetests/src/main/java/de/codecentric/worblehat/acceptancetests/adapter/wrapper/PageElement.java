package de.codecentric.worblehat.acceptancetests.adapter.wrapper;

public enum PageElement {
    ADDBOOKBUTTON("addBook"), BOOKLIST("bookList"), BORROWBOOKBUTTON("borrowBook"), ISBNERROR("isbn-error"),
    RETURNALLBOOKSBUTTON("returnAllBooks"), ERROR("error"), DETAILS_TITLE("details_title");


    private String elementId;

    PageElement(String elementId){
        this.elementId = elementId;
    }

    public String getElementId(){
        return elementId;
    }
}
