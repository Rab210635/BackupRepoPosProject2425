package spengergasse.at.sj2425scherzerrabar.domain;

import org.junit.jupiter.api.*;
import spengergasse.at.sj2425scherzerrabar.FixturesFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class AddressTest {



    @Nested
    public class test_get_methods_address{
        Address address;
        @BeforeEach
        public void init() {
             address = FixturesFactory.libraryAddress();
        }
        @Test
        public void get_city_test(){
            assertThat(address.city()).isEqualTo("Vienna");
        }
        @Test
        public void get_house_nr_and_street_test(){
            assertThat(address.streetAndNumber()).isEqualTo("spengergasse 20");
        }
        @Test
        public void get_zip_test(){
            assertThat(address.zip()).isEqualTo(1050);
        }
    }


}