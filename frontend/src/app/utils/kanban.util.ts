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
  itemToMove: Card
): Card[] => {
  newItemList.splice(
    newItemList.findIndex((item) => item.columnId === itemToMove.columnId),
    1
  );
  itemToMove.columnId = newItemList[0].columnId;
  newItemList.push(itemToMove);
  return newItemList;
};
