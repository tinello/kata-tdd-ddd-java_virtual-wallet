package ar.com.tinello.katas.virtualwallet.exploratory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

class CollectionsTest {

    @Test
    void unmodifiableList() {
        List<String> list = new ArrayList<>();
        list.add("Geeks");

        // Create ImmutableList from List using copyOf()
        List<String> iList = Collections.unmodifiableList(list);
        assertThat(iList.size()).isEqualTo(1);

        // We change List and the changes reflect in iList.
        list.add("For");
        list.add("Geeks");

        assertThat(iList.size()).isEqualTo(3);

        final var thrown = catchThrowable(() -> iList.add("error") );
        assertThat(thrown).isInstanceOf(UnsupportedOperationException.class);

    }
}
