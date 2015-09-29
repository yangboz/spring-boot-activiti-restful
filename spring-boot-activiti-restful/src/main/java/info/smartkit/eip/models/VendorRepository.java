package info.smartkit.eip.models;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel="vendors",path="vendors")
public interface VendorRepository extends PagingAndSortingRepository<Vendor, Long> {
	List<Item> findById(@Param("id") long id);
}