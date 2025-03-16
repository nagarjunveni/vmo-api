package com.example.vmo.service.impl;

import com.example.vmo.dto.EmployeeRequest;
import com.example.vmo.dto.EmployeeResponse;
import com.example.vmo.dto.VendorResponse;
import com.example.vmo.enums.EmploymentType;
import com.example.vmo.model.Employee;
import com.example.vmo.model.Vendor;
import com.example.vmo.repository.EmployeeRepository;
import com.example.vmo.repository.VendorRepository;
import com.example.vmo.service.EmployeeService;
import com.example.vmo.service.VendorService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final VendorRepository vendorRepository;
    private final VendorService vendorService;

    @Override
    @Transactional
    public EmployeeResponse createEmployee(EmployeeRequest request) {
        validateEmployeeRequest(request);

        Employee employee = new Employee();
        updateEmployeeFromRequest(employee, request);

        Employee savedEmployee = employeeRepository.save(employee);
        return mapToEmployeeResponse(savedEmployee);
    }

    @Override
    @Transactional
    public EmployeeResponse updateEmployee(Long id, EmployeeRequest request) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + id));

        // Validate if employmentType is Contractor and vendorId is provided
        if (request.getEmploymentType() != null &&
                request.getEmploymentType() == EmploymentType.Contractor &&
                request.getVendorId() == null) {
            throw new IllegalArgumentException("Vendor ID is required for Contractor employment type");
        }

        updateEmployeeFromRequest(employee, request);

        Employee updatedEmployee = employeeRepository.save(employee);
        return mapToEmployeeResponse(updatedEmployee);
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeResponse getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + id));

        return mapToEmployeeResponse(employee);
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeResponse getEmployeeByEmail(String email) {
        Employee employee = employeeRepository.findByEmailAndStatusTrue(email)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with email: " + email));

        return mapToEmployeeResponse(employee);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeResponse> getAllActiveEmployees() {
        List<Employee> employees = employeeRepository.findByStatusTrue();
        return employees.stream()
                .map(this::mapToEmployeeResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeResponse> getEmployeesByVendorId(Long vendorId) {
        // Validate that the vendor exists
        if (!vendorRepository.existsById(vendorId)) {
            throw new EntityNotFoundException("Vendor not found with id: " + vendorId);
        }

        List<Employee> employees = employeeRepository.findByVendorIdAndStatusTrue(vendorId);
        return employees.stream()
                .map(this::mapToEmployeeResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + id));

        employee.setStatus(false);
        employeeRepository.save(employee);
    }

    private void validateEmployeeRequest(EmployeeRequest request) {
        if (request.getFirstName() == null || request.getFirstName().trim().isEmpty()) {
            throw new IllegalArgumentException("First name is required");
        }

        if (request.getLastName() == null || request.getLastName().trim().isEmpty()) {
            throw new IllegalArgumentException("Last name is required");
        }

        if (request.getContactNumber() == null || request.getContactNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("Contact number is required");
        }

        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }

        if (employeeRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Employee already exists with email: " + request.getEmail());
        }

        if (request.getDateOfBirth() == null) {
            throw new IllegalArgumentException("Date of birth is required");
        }

        if (request.getGender() == null) {
            throw new IllegalArgumentException("Gender is required");
        }

        if (request.getWorkLocation() == null) {
            throw new IllegalArgumentException("Work location is required");
        }

        if (request.getEmploymentType() == null) {
            throw new IllegalArgumentException("Employment type is required");
        }

        if (request.getEmploymentType() == EmploymentType.Contractor && request.getVendorId() == null) {
            throw new IllegalArgumentException("Vendor ID is required for Contractor employment type");
        }

        if (request.getVendorId() != null && !vendorRepository.existsById(request.getVendorId())) {
            throw new EntityNotFoundException("Vendor not found with id: " + request.getVendorId());
        }

        if (request.getIsFreelancer() == null) {
            throw new IllegalArgumentException("Is freelancer flag is required");
        }

        if (request.getDepartment() == null || request.getDepartment().trim().isEmpty()) {
            throw new IllegalArgumentException("Department is required");
        }

        if (request.getEmploymentStatus() == null) {
            throw new IllegalArgumentException("Employment status is required");
        }

        if (request.getStartDate() == null) {
            throw new IllegalArgumentException("Start date is required");
        }

        if (request.getLocation() == null || request.getLocation().trim().isEmpty()) {
            throw new IllegalArgumentException("Location is required");
        }
    }

    private void updateEmployeeFromRequest(Employee employee, EmployeeRequest request) {
        if (request.getFirstName() != null) {
            employee.setFirstName(request.getFirstName());
        }

        if (request.getLastName() != null) {
            employee.setLastName(request.getLastName());
        }

        if (request.getMiddleName() != null) {
            employee.setMiddleName(request.getMiddleName());
        }

        if (request.getContactNumber() != null) {
            employee.setContactNumber(request.getContactNumber());
        }

        if (request.getEmail() != null) {
            employee.setEmail(request.getEmail());
        }

        if (request.getVendorId() != null) {
            employee.setVendorId(request.getVendorId());
        }

        if (request.getProfilePicture() != null && !request.getProfilePicture().trim().isEmpty()) {
            // Convert Base64 string to byte array
            employee.setProfilePicture(Base64.getDecoder().decode(request.getProfilePicture()));
        }

        if (request.getDateOfBirth() != null) {
            employee.setDateOfBirth(request.getDateOfBirth());
        }

        if (request.getGender() != null) {
            employee.setGender(request.getGender());
        }

        if (request.getWorkLocation() != null) {
            employee.setWorkLocation(request.getWorkLocation());
        }

        if (request.getEmploymentType() != null) {
            employee.setEmploymentType(request.getEmploymentType());
        }

        if (request.getIsFreelancer() != null) {
            employee.setFreelancer(request.getIsFreelancer());
        }

        if (request.getDepartment() != null) {
            employee.setDepartment(request.getDepartment());
        }

        if (request.getEmploymentStatus() != null) {
            employee.setEmploymentStatus(request.getEmploymentStatus());
        }

        if (request.getStartDate() != null) {
            employee.setStartDate(request.getStartDate());
        }

        if (request.getLocation() != null) {
            employee.setLocation(request.getLocation());
        }
    }

    private EmployeeResponse mapToEmployeeResponse(Employee employee) {
        EmployeeResponse response = new EmployeeResponse();
        response.setId(employee.getId());
        response.setFirstName(employee.getFirstName());
        response.setLastName(employee.getLastName());
        response.setMiddleName(employee.getMiddleName());
        response.setContactNumber(employee.getContactNumber());
        response.setEmail(employee.getEmail());
        response.setVendorId(employee.getVendorId());

        // Convert byte array to Base64 string
        if (employee.getProfilePicture() != null) {
            response.setProfilePicture(Base64.getEncoder().encodeToString(employee.getProfilePicture()));
        }

        response.setDateOfBirth(employee.getDateOfBirth());
        response.setGender(employee.getGender());
        response.setWorkLocation(employee.getWorkLocation());
        response.setEmploymentType(employee.getEmploymentType());
        response.setFreelancer(employee.isFreelancer());
        response.setDepartment(employee.getDepartment());
        response.setEmploymentStatus(employee.getEmploymentStatus());
        response.setStartDate(employee.getStartDate());
        response.setLocation(employee.getLocation());
        response.setCreatedDate(employee.getCreatedDate());
        response.setUpdatedDate(employee.getUpdatedDate());
        response.setStatus(employee.isStatus());

        // Get vendor information if vendorId is present
        if (employee.getVendorId() != null) {
            try {
                VendorResponse vendorResponse = vendorService.getVendorById(employee.getVendorId());
                response.setVendor(vendorResponse);
            } catch (EntityNotFoundException e) {
                // Vendor not found, but we still want to return the employee
            }
        }

        return response;
    }
}