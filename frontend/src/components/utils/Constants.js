export const PAGE_SIZE = 8;

export const SEARCH_PAGE_SIZE = 16;

export const NAME_PATTERN = /^[A-Za-zА-Яа-яЁё\-]{1,50}$/;

export const PASSWORD_PATTERN_STRING = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*_=+\\-]).{8,60}$";

export const PASSWORD_MESSAGE = "Пароль должен состоять из 8–60 латинских символов и содержать как минимум одну заглавную букву, одну строчную букву, одну цифру и один спецсимвол (!@#$%^&*=+-_)";

export const PHONE_PATTERN_STRING = "^((8|\\+374|\\+994|\\+995|\\+375|\\+7|\\+380|\\+38|\\+996|\\+998|\\+993)[\\- ]?)?\\(?\\d{3,5}\\)?[\\- ]?\\d{1}[\\- ]?\\d{1}[\\- ]?\\d{1}[\\- ]?\\d{1}[\\- ]?\\d{1}(([\\- ]?\\d{1})?[\\- ]?\\d{1})?$";

export const LocationTypeMapping = {
  UNKNOWN: 'Неизвестен',
  OFFICE: 'Кабинет',
  STORAGE: 'Склад',
  SERVER_ROOM: 'Серверная',
  TECHNICAL_ROOM: 'Техническое',
  CLOAKROOM: 'Гардероб',
  BATHROOM: 'Санузел',
  GYM: 'Спортзал',
  CAFETERIA: 'Кафетерий'
};

export const UserRoleMapping = {
  SUPER_ADMIN: 'Супер-администратор',
  ADMIN: 'Администратор',
  USER: 'Пользователь'
}
