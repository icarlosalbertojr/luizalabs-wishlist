package com.challenge.wishlist.core.usecase;

public interface UseCase<I, O> {

    O execute(I input);

}
