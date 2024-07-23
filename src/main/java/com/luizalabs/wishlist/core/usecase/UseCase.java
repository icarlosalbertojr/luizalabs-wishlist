package com.luizalabs.wishlist.core.usecase;

public interface UseCase<I, O> {

    O execute(I input);

}
