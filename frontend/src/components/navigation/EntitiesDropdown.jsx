import React from 'react';
import { Nav, NavDropdown } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import { BsListUl } from 'react-icons/bs';

const navItems = [
  { to: '/admin/buildings', label: 'Здания' },
  { to: '/admin/departments', label: 'Отделы' },
  { to: '/admin/devices', label: 'Устройства' },
  { to: '/admin/device-models', label: 'Модели устройств' },
  { to: '/admin/device-types', label: 'Типы устройств' },
  { to: '/admin/employees', label: 'Сотрудники' },
  { to: '/admin/locations', label: 'Помещения' },
  { to: '/admin/manufacturers', label: 'Производители' },
  { to: '/admin/positions', label: 'Должности' },
  { to: '/admin/structure-element-models', label: 'Модели элементов структуры' },
  { to: '/admin/structure-element-types', label: 'Типы элементов структуры' }
];

const EntitiesDropdown = () => {
  const dropdownTitle = (
    <span className="text-white">
      <BsListUl className="me-2 align-text-bottom" />
      Сущности
    </span>
  );

  return (
    <Nav>
      <NavDropdown title={dropdownTitle} id="entities-dropdown" align="end" className="nav-link">
        {
          navItems.map((item, index) => (
            <React.Fragment key={index}>
              <NavDropdown.Item
                as={Link}
                to={item.to}
              >
                {item.label}
              </NavDropdown.Item>
              {index < navItems.length - 1 && (
                <NavDropdown.Divider />
              )}
            </React.Fragment>
          ))
        }
      </NavDropdown>
    </Nav>
  );
}

export default EntitiesDropdown;