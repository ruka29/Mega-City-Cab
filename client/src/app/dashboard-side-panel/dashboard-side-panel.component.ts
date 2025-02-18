import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-dashboard-side-panel',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard-side-panel.component.html',
  styleUrl: './dashboard-side-panel.component.scss',
})
export class DashboardSidePanelComponent {
  @Input() activeTab: string = ''; // Get the active tab from parent
  @Output() tabChange = new EventEmitter<string>(); // Emit new tab to parent

  setActive(tab: string) {
    this.tabChange.emit(tab); // Notify parent about the tab change
  }
}
