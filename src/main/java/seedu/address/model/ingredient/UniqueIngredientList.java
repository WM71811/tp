package seedu.address.model.ingredient;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.ingredient.exceptions.DuplicateIngredientException;
import seedu.address.model.ingredient.exceptions.IngredientNotFoundException;
import seedu.address.model.ingredient.exceptions.NoChangeIngredientException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;

public class UniqueIngredientList implements Iterable<Ingredient> {

    private final ObservableList<Ingredient> internalList = FXCollections.observableArrayList(
            new Ingredient(new IngredientName("Milk")),
            new Ingredient(new IngredientName("Pearl")),
            new Ingredient(new IngredientName("Boba")),
            new Ingredient(new IngredientName("Oolong Tea")),
            new Ingredient(new IngredientName("Brown Sugar"))
    );
    private final ObservableList<Ingredient> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);
    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean contains(Ingredient toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameIngredient);
    }

    public void setIngredient(Ingredient target, Ingredient newAmount) {

        requireAllNonNull(target, newAmount);

        int index = internalList.indexOf(target);

        if (index == -1) {
            throw new IngredientNotFoundException();
        }

        internalList.set(index, newAmount);
    }

    public void setIngredients(UniqueIngredientList replacement) {
        requireNonNull(replacement);

        internalList.setAll(replacement.internalList);
    }

    public void setIngredients(List<Ingredient> ingredients) {
        requireAllNonNull(ingredients);
        if (!ingredientsAreUnique(ingredients)) {
            throw new NoChangeIngredientException();
        }
        internalList.setAll(ingredients);
    }

    /**
     * Adds an ingredient to the list.
     * The ingredient must not already exist in the list.
     */
    public void add(Ingredient toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateIngredientException();
        }
        internalList.add(toAdd);
    }

    public ObservableList<Ingredient> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }


    @Override
    public Iterator<Ingredient> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueIngredientList // instanceof handles nulls
                && internalList.equals(((UniqueIngredientList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    private boolean ingredientsAreUnique(List<Ingredient> ingredients) {
        for (int i = 0; i < ingredients.size() - 1; i++) {
            for (int j = i + 1; j < ingredients.size(); j++) {
                if (ingredients.get(i).isSameIngredient(ingredients.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

}
