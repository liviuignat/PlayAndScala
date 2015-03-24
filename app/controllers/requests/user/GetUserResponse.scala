package controllers.requests.user

/**
 * Created by liviu.ignat on 3/23/2015.
 */
case class GetUserResponse ( id: String,
                        email: String,
                        firstName: String,
                        lastName: String,
                        isActive: Boolean)
