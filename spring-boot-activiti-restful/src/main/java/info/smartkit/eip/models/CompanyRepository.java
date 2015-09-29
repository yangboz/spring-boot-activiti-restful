package info.smartkit.eip.models;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel="company",path="company")
public interface CompanyRepository extends PagingAndSortingRepository<Company, Long> {
	List<Company> findById(@Param("id") long id);
}
