package com.invillia.acme.integrated.resource.v1;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.invillia.acme.util.ObjectMapperUtil.*;

import com.invillia.acme.InvilliaApplication;
import com.invillia.acme.model.entity.ProviderEntity;
import com.invillia.acme.model.repository.ProviderRepository;
import com.invillia.acme.resource.v1.domain.Provider;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = InvilliaApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class ProviderResourceTest {

  @Autowired
  private MockMvc mvc;

  @Autowired
  private ProviderRepository providerRepository;

  @Before
  public void setupProviders() {
    if (providerRepository.count() < 1)
      providerRepository.saveAll(providerEntities());
  }

  @Test
  public void createProviderSuccess() throws Exception {
    mvc.perform(post("/v1/providers")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(serializeToString(Provider.builder().name("Name Fourth").address("Address Fourth").build())))
        .andDo(print())
        .andExpect(status().isCreated());
  }

  @Test
  public void createProviderError() throws Exception {
    mvc.perform(post("/v1/providers")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(serializeToString(createProvider(null, null))))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

  @Test
  public void updateProviderSuccess() throws Exception {
    mvc.perform(put("/v1/providers/1")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(serializeToString(Provider.builder().name("Name First AA").address("Address First AA").build())))
        .andDo(print())
        .andExpect(status().isNoContent());
  }

  @Test
  public void updateProviderError() throws Exception {
    mvc.perform(put("/v1/providers/1")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(serializeToString(Provider.builder().name(null).address(null).build())))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

  @Test
  public void getByParamsSuccess() throws Exception {
    mvc.perform(get("/v1/providers?name=Second&address=Second"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$", hasSize(1)));
  }

  @Test
  public void getByParamsNoResult() throws Exception {
    mvc.perform(get("/v1/providers?name=Second&address=Third"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$", hasSize(0)));
  }

  private List<ProviderEntity> providerEntities() {
    List<ProviderEntity> providerEntities = new ArrayList<>();
    providerEntities.add(ProviderEntity.builder().name("Name First").address("Address First").build());
    providerEntities.add(ProviderEntity.builder().name("Name Second").address("Address Second").build());
    providerEntities.add(ProviderEntity.builder().name("Name Third").address("Address Third").build());
    return providerEntities;
  }

  private Provider createProvider(final String name, final String address) {
    return Provider.builder().address(address).name(name).build();
  }

}
