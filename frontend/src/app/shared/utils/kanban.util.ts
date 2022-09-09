import { Column } from 'src/app/core/models/kanban.model';

export const sortColumns = (array: Column[]): Column[] => {
  array.forEach((element, index) => (element.order = index));
  return array;
};
