import { Card, Column } from 'src/app/models/kanban.model';

export const sortColumns = (array: Column[]): Column[] => {
  array.forEach((element, index) => (element.order = index));
  return array;
};

export const sortCards = (array: Card[]): Card[] => {
  array.forEach((element, index) => (element.order = index));
  return array;
};

export const updateColumForCard = (
  newItemList: Card[],
  itemToMove: Card,
  newIndex: number,
  newColumn: string
): Card[] => {
  newItemList.splice(
    newItemList.findIndex((item) => item.columnId === itemToMove.columnId),
    1
  );
  itemToMove.columnId = newColumn;
  newItemList.splice(newIndex, 0, itemToMove);
  return newItemList;
};
