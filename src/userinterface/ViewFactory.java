package userinterface;

import impresario.IModel;

//==============================================================================
public class ViewFactory {

	public static View createView(String viewName, IModel model)
	{
		if(viewName.equals("LibrarianView"))
		{
			return new LibrarianView(model);
		}
		else if(viewName.equals("NewBookView"))
		{
			return new NewBookView(model);
		}else if(viewName.equals("NewPatronView"))
		{
			return new NewPatronView(model);
		} else if(viewName.equals("BookCollectionView")){
		    return new BookCollectionView(model);
        } else if(viewName.equals("PatronCollectionView")){
            return new PatronCollectionView(model);
        }

//		else if(viewName.equals("SearchBookView"))
//		{
//			return new SearchBookView(model);
//		}else if(viewName.equals("SearchPatronView"))
//		{
//			return new SearchPatronView(model);
//		}
		return null;
	} 


	/*
	public static Vector createVectorView(String viewName, IModel model)
	{
		if(viewName.equals("SOME VIEW NAME") == true)
		{
			//return [A NEW VECTOR VIEW OF THAT NAME TYPE]
		}
		else
			return null;
	}
	*/

}
