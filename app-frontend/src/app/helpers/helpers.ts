import { Category } from "../models/Category.mode";
import { Label } from "../models/Label.model";

export function capitalizeLabels(labels: Label[]): Label[] {
    return labels.map(label => ({
        ...label,
        name: label.name.charAt(0).toUpperCase() + label.name.slice(1)
    }));
}

export function lowercaseLabels(labels: Label[]): Label[] {
    return labels.map(label => ({
        ...label,
        name: label.name.toLowerCase()
    }));
}

export function capitalizeCategories(categories: Category[]): Category[] {
    return categories.map(category => ({
        ...category,
        name: category.name.charAt(0).toUpperCase() + category.name.slice(1)
    }));
}

export function lowercaseCategories(categories: Category[]): Category[] {
    return categories.map(category => ({
        ...category,
        name: category.name.toLowerCase()
    }));
}
