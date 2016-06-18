package ca.pureplugins.jkik.interfaces;

public abstract interface IUser
{
	String getUsername();

	String getFirstName();

	String getLastName();

	String getProfilePicture();

	long getProfilePictureLastModified();
}