package ca.pureplugins.jkik.interfaces;

public abstract interface User
{
	String getUsername();

	String getFirstName();

	String getLastName();

	String getProfilePicture();

	long getProfilePictureLastModified();
}