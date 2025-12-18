import React from 'react';
import { Nav, NavDropdown } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import { BsFileEarmarkBarGraph } from 'react-icons/bs';

const reportItems = [
  { to: '/admin/reports/devices-by-date', label: 'Устройства по дате' },
  { to: '/admin/reports/devices-with-structure', label: 'Устройства со структурой' },
  { to: '/admin/reports/locations-with-employees', label: 'Помещения с сотрудниками' }
];

const ReportsDropdown = () => {
  const dropdownTitle = (
    <span className="text-white">
      <BsFileEarmarkBarGraph className="me-2 align-text-bottom" />
      Отчеты
    </span>
  );

  return (
    <Nav>
      <NavDropdown title={dropdownTitle} id="reports-dropdown" align="end" className="nav-link">
        {
          reportItems.map((item, index) => (
            <React.Fragment key={index}>
              <NavDropdown.Item
                as={Link}
                to={item.to}
              >
                {item.label}
              </NavDropdown.Item>
              {index < reportItems.length - 1 && (
                <NavDropdown.Divider />
              )}
            </React.Fragment>
          ))
        }
      </NavDropdown>
    </Nav>
  );
}

export default ReportsDropdown;