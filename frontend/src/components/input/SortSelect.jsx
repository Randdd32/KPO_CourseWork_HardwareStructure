import { Form } from 'react-bootstrap';
import PropTypes from 'prop-types';

const SortSelect = ({ sortType, handleSortChange }) => {
  return (
    <Form.Select
      value={sortType}
      aria-label="sort-select"
      className="border-black device-list-sort-select"
      onChange={handleSortChange}
    >
      <option value="PURCHASE_DATE_DESC">Сначала новые</option>
      <option value="PURCHASE_DATE_ASC">Сначала старые</option>
      <option value="PRICE_ASC">Сначала дешевые</option>
      <option value="PRICE_DESC">Сначала дорогие</option>
      <option value="IS_WORKING_DESC">Сначала рабочие</option>
      <option value="IS_WORKING_ASC">Сначала сломанные</option>
    </Form.Select>
  );
};

SortSelect.propTypes = {
  sortType: PropTypes.string.isRequired,
  handleSortChange: PropTypes.func.isRequired
};

export default SortSelect;