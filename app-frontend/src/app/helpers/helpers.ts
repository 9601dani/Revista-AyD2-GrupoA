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
