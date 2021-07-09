package org.acme;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.acme.users.UserProfile;

public class UniMultiExample {

    UserService users;

    public void example() {
        //tag::code[]
        Uni<UserProfile> uni = UserProfile.findByName("leia");
        Multi<UserProfile> multi = UserProfile.streamAll();
        //end::code[]
    }

    public void sub() {
        //tag::subscription[]
        Uni<UserProfile> uni = users.getUserByName("leia");
        Multi<UserProfile> multi = users.getAllUsers();

        uni.subscribe().with(
                user -> System.out.println("User is " + user.name),
                failure -> System.out.println("D'oh! " + failure)
        );

        multi.subscribe().with(
                user -> System.out.println("User is " + user.name),
                failure -> System.out.println("D'oh! " + failure),
                () -> System.out.println("No more user")
        );
        //end::subscription[]
    }

    public void uni() {
        //tag::uni[]
        Uni<UserProfile> uni = users.getUserByName("leia");
        uni
                .onItem().transform(user -> user.name)
                .onFailure().recoverWithItem("anonymous")
                .subscribe().with(
                    name -> System.out.println("User is" + name)
        );
        //end::uni[]
    }

    public void multi() {
        // tag::multi[]
        Multi<UserProfile> multi = users.getAllUsers();
        multi
                .onItem().transform(user -> user.name.toLowerCase())
                .select().where(name -> name.startsWith("l"))
                .collect().asList()
                .subscribe().with(
                        list -> System.out.println("User names starting with `l`" + list)
        );
        //end::multi[]
    }
}
